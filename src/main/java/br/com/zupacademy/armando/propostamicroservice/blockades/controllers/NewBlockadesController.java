package br.com.zupacademy.armando.propostamicroservice.blockades.controllers;

import br.com.zupacademy.armando.propostamicroservice.blockades.entities.Blockade;
import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.cards.enums.CardStatus;
import br.com.zupacademy.armando.propostamicroservice.cards.repository.CardRepository;
import br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler.ApiGenericException;
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

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private EntityManager entityManager;

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

        String ip = httpServletRequest.getRemoteAddr();
        String userAgent = httpServletRequest.getHeader("User-Agent");
        Blockade blockade = new Blockade(ip, userAgent, card);
        entityManager.persist(blockade);
        card.setStatus(CardStatus.BLOQUEADO);
        cardRepository.save(card);
        return ResponseEntity.ok().build();
    }

}
