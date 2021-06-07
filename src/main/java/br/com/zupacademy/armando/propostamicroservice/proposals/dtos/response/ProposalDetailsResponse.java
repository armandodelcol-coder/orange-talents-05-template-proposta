package br.com.zupacademy.armando.propostamicroservice.proposals.dtos.response;

import br.com.zupacademy.armando.propostamicroservice.proposals.entities.Proposal;
import br.com.zupacademy.armando.propostamicroservice.proposals.enums.ProposalStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ProposalDetailsResponse {

    @JsonProperty
    public Long id;
    @JsonProperty
    public String document;
    @JsonProperty
    public String email;
    @JsonProperty
    public String name;
    @JsonProperty
    public String address;
    @JsonProperty
    public BigDecimal salary;
    @JsonProperty
    public ProposalStatus status;
    @JsonProperty
    public CardProposalResponse card;

    public ProposalDetailsResponse(Proposal proposal) {
        this.id = proposal.getId();
        this.document = proposal.getDocument();;
        this.email = proposal.getEmail();
        this.name = proposal.getName();
        this.address = proposal.getAddress();
        this.salary = proposal.getSalary();
        this.status = proposal.getStatus();
        this.card = proposal.getCard() == null ? new CardProposalResponse() : new CardProposalResponse(proposal.getCard());
    }

}
