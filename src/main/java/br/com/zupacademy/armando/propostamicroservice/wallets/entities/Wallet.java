package br.com.zupacademy.armando.propostamicroservice.wallets.entities;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.wallets.enums.WalletName;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "tb_wallets")
public class Wallet {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank @Email
    @Column(nullable = false)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalletName name;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Deprecated
    public Wallet() {
    }

    public Wallet(String email, WalletName name, Card card) {
        this.email = email;
        this.name = name;
        this.card = card;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public WalletName getName() {
        return name;
    }
}
