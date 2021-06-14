package br.com.zupacademy.armando.propostamicroservice.travels.controllers;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.cards.repository.CardRepository;
import br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler.ApiGenericException;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.AccountsClient;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos.CardTravelNotifyRequest;
import br.com.zupacademy.armando.propostamicroservice.travels.dtos.request.NewTravelRequest;
import br.com.zupacademy.armando.propostamicroservice.travels.entities.Travel;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
public class NewTravelController {

    private final Logger logger = LoggerFactory.getLogger(NewTravelController.class);

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AccountsClient accountsClient;

    @PostMapping("/cartoes/{cardIdentifier}/viagens")
    @Transactional
    public ResponseEntity<?> newTravel(
            @PathVariable("cardIdentifier") String cardIdentifier,
            @RequestBody @Valid NewTravelRequest newTravelRequest,
            HttpServletRequest httpServletRequest
    ) throws ApiGenericException {
        Optional<Card> possibleCard = cardRepository.findByIdentifierCode(cardIdentifier);
        if (possibleCard.isEmpty()) throw new ApiGenericException(HttpStatus.NOT_FOUND, "Cartão não encontrado");

        Card card = possibleCard.get();
        String ip = httpServletRequest.getRemoteAddr();
        String userAgent = httpServletRequest.getHeader("User-Agent");
        Travel newTravel = newTravelRequest.toModel(ip, userAgent, card);
        accountsClientTravelNotification(card, newTravel);
        entityManager.persist(newTravel);
        return ResponseEntity.ok().build();
    }

    private void accountsClientTravelNotification(Card card, Travel travel) throws ApiGenericException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        CardTravelNotifyRequest cardTravelNotifyRequest = new CardTravelNotifyRequest(
                travel.getDestiny(),
                travel.getEndDateTimeToString(formatter)
        );
        try {
            accountsClient.travelNotify(card.getId(), cardTravelNotifyRequest);
        } catch (FeignException e) {
            logger.warn("Não foi possível comunicar o aviso de viagem para o serviço externo de cartões");
            logger.info("Status retornado na api externa: " + e.status());
            logger.error(e.contentUTF8());
            throw new ApiGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível realizar a notificação de viagem, entre em contato com o administrador.");
        }
    }

}
