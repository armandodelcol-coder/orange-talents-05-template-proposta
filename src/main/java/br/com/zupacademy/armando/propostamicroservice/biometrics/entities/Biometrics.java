package br.com.zupacademy.armando.propostamicroservice.biometrics.entities;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "tb_biometrics")
public class Biometrics {

    @Id
    private String identifier;

    @NotBlank
    @Column(nullable = false)
    private String fingerPrintBase64;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    public Biometrics(String fingerPrintBase64, Card card) {
        this.fingerPrintBase64 = fingerPrintBase64;
        this.card = card;
    }

    public String getIdentifier() {
        return identifier;
    }

    @PrePersist
    private void prePersist() {
        this.identifier = UUID.randomUUID().toString();
    }

}
