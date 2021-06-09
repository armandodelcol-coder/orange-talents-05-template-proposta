package br.com.zupacademy.armando.propostamicroservice.cards.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @NotNull
    @Column(columnDefinition = "tinyint unsigned", nullable = false)
    private Integer dueDateDay;

    @Column(nullable = false)
    private String identifierCode;

    @Deprecated
    public Card() {
    }

    public Card(String id, LocalDateTime emissionDate, String owner, BigDecimal limit, Integer dueDateDay) {
        this.id = id;
        this.emissionDate = emissionDate;
        this.owner = owner;
        this.limit = limit;
        this.dueDateDay = dueDateDay;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmissionDate() {
        return emissionDate;
    }

    public String getOwner() {
        return owner;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public Integer getDueDateDay() {
        return dueDateDay;
    }

    public String getIdentifierCode() {
        return identifierCode;
    }

    @PrePersist
    private void prePersist() {
        this.identifierCode = UUID.randomUUID().toString();
    }

}
