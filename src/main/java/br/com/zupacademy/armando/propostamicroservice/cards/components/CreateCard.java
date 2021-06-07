package br.com.zupacademy.armando.propostamicroservice.cards.components;

import br.com.zupacademy.armando.propostamicroservice.cards.dtos.accountsclient.response.CardResponse;
import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.cards.entities.CardDueDate;
import br.com.zupacademy.armando.propostamicroservice.cards.repositories.CardRepository;
import br.com.zupacademy.armando.propostamicroservice.cards.repositories.CardDueDateRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateCard {

    private CardRepository cardRepository;
    private CardDueDateRepository cardDueDateRepository;

    public CreateCard(CardRepository cardRepository, CardDueDateRepository cardDueDateRepository) {
        this.cardRepository = cardRepository;
        this.cardDueDateRepository = cardDueDateRepository;
    }

    public Card newCard(CardResponse cardResponse) {
        Card newCard = cardResponse.toModel();
        cardRepository.save(newCard);
        CardDueDate newCardCardDueDate = cardResponse.getVencimentoToModel(newCard);
        cardDueDateRepository.save(newCardCardDueDate);
        return newCard;
    }

}
