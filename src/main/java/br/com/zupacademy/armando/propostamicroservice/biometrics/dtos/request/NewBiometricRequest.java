package br.com.zupacademy.armando.propostamicroservice.biometrics.dtos.request;

import br.com.zupacademy.armando.propostamicroservice.biometrics.entities.Biometrics;
import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.core.validations.Base64;

import javax.validation.constraints.NotBlank;

public class NewBiometricRequest {

    @NotBlank @Base64
    private String fingerPrint;

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public Biometrics toModel(Card card) {
        return new Biometrics(fingerPrint, card);
    }

}
