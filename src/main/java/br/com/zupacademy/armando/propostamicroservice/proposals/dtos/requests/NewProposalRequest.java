package br.com.zupacademy.armando.propostamicroservice.proposals.dtos.requests;

import br.com.zupacademy.armando.propostamicroservice.core.validations.CPFOrCNPJ;
import br.com.zupacademy.armando.propostamicroservice.proposals.entities.Proposal;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NewProposalRequest {

    @JsonProperty
    @NotBlank @CPFOrCNPJ
    public final String document;
    @JsonProperty
    @NotBlank @Email
    final String email;
    @JsonProperty
    @NotBlank
    final String name;
    @JsonProperty
    @NotBlank
    final String address;
    @JsonProperty
    @NotNull @Positive
    final BigDecimal salary;

    public NewProposalRequest(String document, String email, String name, String address, BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public Proposal toModel() {
        return new Proposal(
                this.document,
                this.email,
                this.name,
                this.address,
                this.salary
        );
    }

}
