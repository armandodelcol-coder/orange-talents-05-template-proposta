package br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewWalletAccountsRequest {

    @JsonProperty
    public final String email;
    @JsonProperty
    public final String carteira;

    public NewWalletAccountsRequest(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

}
