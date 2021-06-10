package br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardBlockadeRequest {

    @JsonProperty
    public String sistemaResponsavel;

    public CardBlockadeRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

}
