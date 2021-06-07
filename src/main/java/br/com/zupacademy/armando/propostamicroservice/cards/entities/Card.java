package br.com.zupacademy.armando.propostamicroservice.cards.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_card")
public class Card {

    @Id
    private String id;

    @NotNull
    @Column(columnDefinition = "datetime", nullable = false)
    private LocalDateTime emissionDate;

    @NotNull
    @Column(nullable = false)
    private String owner;

    @NotNull
    @Column(columnDefinition = "decimal", precision = 2, scale = 10, nullable = false, name = "credit_limit")
    private BigDecimal limit;

    @Deprecated
    public Card() {
    }

    public Card(String id, LocalDateTime emissionDate, String owner, BigDecimal limit) {
        this.id = id;
        this.emissionDate = emissionDate;
        this.owner = owner;
        this.limit = limit;
    }

}
