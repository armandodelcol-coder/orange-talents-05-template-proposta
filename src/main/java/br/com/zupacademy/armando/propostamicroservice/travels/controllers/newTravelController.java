package br.com.zupacademy.armando.propostamicroservice.travels.controllers;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.cards.repository.CardRepository;
import br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler.ApiGenericException;
import br.com.zupacademy.armando.propostamicroservice.travels.dtos.request.NewTravelRequest;
import br.com.zupacademy.armando.propostamicroservice.travels.entities.Travel;
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
import java.util.Optional;

@RestController
public class newTravelController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private EntityManager entityManager;

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
        entityManager.persist(newTravel);
        return ResponseEntity.ok().build();
    }

}
