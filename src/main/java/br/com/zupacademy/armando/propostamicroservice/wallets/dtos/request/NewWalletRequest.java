package br.com.zupacademy.armando.propostamicroservice.wallets.dtos.request;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.core.validations.ValueOfEnum;
import br.com.zupacademy.armando.propostamicroservice.wallets.entities.Wallet;
import br.com.zupacademy.armando.propostamicroservice.wallets.enums.WalletName;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NewWalletRequest {

    @JsonProperty
    @NotBlank @Email
    public String email;
    @JsonProperty
    @NotBlank @ValueOfEnum(enumClass = WalletName.class)
    public String name;

    public NewWalletRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public Wallet toModel(Card card) {
        return new Wallet(this.email, WalletName.valueOf(this.name), card);
    }

}
