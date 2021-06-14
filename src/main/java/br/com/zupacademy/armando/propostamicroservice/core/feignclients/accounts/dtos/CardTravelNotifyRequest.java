package br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardTravelNotifyRequest {

    @JsonProperty
    public final String destino;
    @JsonProperty
    public final String validoAte;

    public CardTravelNotifyRequest(String destino, String validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

}
