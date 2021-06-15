package br.com.zupacademy.armando.propostamicroservice.proposals.entities;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.core.utils.ProposalDocumentEncrypt;
import br.com.zupacademy.armando.propostamicroservice.proposals.enums.ProposalStatus;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_proposal")
public class Proposal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String document;

    @NotBlank @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String address;

    @NotNull @Positive
    @Column(nullable = false)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private ProposalStatus status;

    @OneToOne
    @JoinColumn(name = "card_number")
    private Card card;

    @Deprecated
    public Proposal() {
    }

    public Proposal(String document, String email, String name, String address, BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public ProposalStatus getStatus() {
        return status;
    }

    public Card getCard() {
        return card;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @PrePersist
    private void prePersist() {
        this.document = ProposalDocumentEncrypt.genSecureHash(this.document);
    }

}
