package br.com.zupacademy.armando.propostamicroservice.biometrics.controllers;

import br.com.zupacademy.armando.propostamicroservice.biometrics.dtos.request.NewBiometricRequest;
import br.com.zupacademy.armando.propostamicroservice.biometrics.entities.Biometrics;
import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.cards.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class NewBiometricController {

    @Autowired
    private CardRepository cardRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/cartoes/{cardIdentifier}/biometrias")
    @Transactional
    public ResponseEntity<?> newBiometric(@PathVariable("cardIdentifier") String cardIdentifier,
                                          @RequestBody @Valid NewBiometricRequest newBiometricRequest,
                                          UriComponentsBuilder uriComponentsBuilder) {
        Optional<Card> possibleCard = cardRepository.findByIdentifierCode(cardIdentifier);
        if (possibleCard.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Biometrics newBiometrics = newBiometricRequest.toModel(possibleCard.get());
        entityManager.persist(newBiometrics);
        URI path = uriComponentsBuilder.path("/biometrics/{id}").buildAndExpand(newBiometrics.getIdentifier()).toUri();
        return ResponseEntity.created(path).build();
    }

}
