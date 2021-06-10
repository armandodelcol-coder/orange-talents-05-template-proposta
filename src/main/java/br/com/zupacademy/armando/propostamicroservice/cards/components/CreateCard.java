package br.com.zupacademy.armando.propostamicroservice.cards.components;

import br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos.CardResponse;
import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
public class CreateCard {

    private EntityManager entityManager;

    public CreateCard(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Card newCard(CardResponse cardResponse) {
        Card newCard = cardResponse.toModel();
        entityManager.persist(newCard);
        return newCard;
    }

}
