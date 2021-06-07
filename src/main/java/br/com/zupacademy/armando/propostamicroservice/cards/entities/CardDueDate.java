package br.com.zupacademy.armando.propostamicroservice.cards.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_card_due_date")
public class CardDueDate {

    @Id
    private String id;

    @NotNull
    @Column(nullable = false, columnDefinition = "tinyint unsigned")
    private Integer day;

    @NotNull
    @Column(columnDefinition = "datetime", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Deprecated
    public CardDueDate() {
    }

    public CardDueDate(String id, Integer day, LocalDateTime createdAt, Card card) {
        this.id = id;
        this.day = day;
        this.createdAt = createdAt;
        this.card = card;
    }

}
