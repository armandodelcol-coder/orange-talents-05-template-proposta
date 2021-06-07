package br.com.zupacademy.armando.propostamicroservice.proposals.dtos.response;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CardProposalResponse {

    @JsonProperty
    public String id;
    @JsonProperty
    public LocalDateTime emissionDate;
    @JsonProperty
    public String owner;
    @JsonProperty
    public BigDecimal limit;
    @JsonProperty
    public Integer dueDateDay;

    public CardProposalResponse() {
    }

    public CardProposalResponse(Card card) {
        this.id = card.getId();
        this.emissionDate = card.getEmissionDate();
        this.owner = card.getOwner();
        this.limit = card.getLimit();
        this.dueDateDay = card.getDueDateDay();
    }

}
