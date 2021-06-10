package br.com.zupacademy.armando.propostamicroservice.blockades.controllers;

import br.com.zupacademy.armando.propostamicroservice.blockades.entities.Blockade;
import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.cards.enums.CardStatus;
import br.com.zupacademy.armando.propostamicroservice.cards.repository.CardRepository;
import br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler.ApiGenericException;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.AccountsClient;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos.CardBlockadeRequest;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos.CardBlockadeResponse;
import br.com.zupacademy.armando.propostamicroservice.proposals.controllers.NewProposalController;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@RestController
public class NewBlockadesController {

    private final Logger logger = LoggerFactory.getLogger(NewProposalController.class);

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AccountsClient accountsClient;

    @PostMapping("/cartoes/{cardIdentifier}/bloqueio")
    @Transactional
    public ResponseEntity<?> newBlockade(
            @PathVariable String cardIdentifier,
            HttpServletRequest httpServletRequest
    ) throws ApiGenericException {
        Optional<Card> possibleCard = cardRepository.findByIdentifierCode(cardIdentifier);
        if (possibleCard.isEmpty()) throw new ApiGenericException(HttpStatus.NOT_FOUND, "Cartão não encontrado");
        Card card = possibleCard.get();

        if (card.isBlocked()) {
            throw new ApiGenericException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já está bloqueado");
        }

        Blockade newBlockade = createdBlockade(httpServletRequest, card);
        notifyCardBlockadeToAccountsClient(card);
        card.setStatus(CardStatus.BLOQUEADO);
        newBlockade.blockadeWithSuccess();
        cardRepository.save(card);
        entityManager.merge(newBlockade);
        return ResponseEntity.ok().build();
    }

    private void notifyCardBlockadeToAccountsClient(Card card) throws ApiGenericException {
        CardBlockadeRequest cardBlockadeRequest = new CardBlockadeRequest("api-propostas");
        try {
            CardBlockadeResponse cardBlockadeResponse = accountsClient.blockingCard(card.getId(), cardBlockadeRequest);
        } catch (FeignException e) {
            logger.error("Não foi possível comunicar o bloqueio do cartão para o serviço externo de cartões");
            throw new ApiGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Bloqueio não foi efetuado com sucesso.");
        }
    }

    @Transactional
    private Blockade createdBlockade(HttpServletRequest httpServletRequest, Card card) {
        String ip = httpServletRequest.getRemoteAddr();
        String userAgent = httpServletRequest.getHeader("User-Agent");
        Blockade blockade = new Blockade(ip, userAgent, card);
        entityManager.persist(blockade);
        return blockade;
    }

}
