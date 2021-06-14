package br.com.zupacademy.armando.propostamicroservice.travels.entities;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "tb_travels")
public class Travel {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank
    @Column(nullable = false)
    private String destiny;

    @NotNull
    @FutureOrPresent
    @Column(columnDefinition = "datetime", nullable = false)
    private LocalDateTime endDate;

    @CreationTimestamp
    @Column(columnDefinition = "datetime", nullable = false)
    private LocalDateTime createdAt;

    @NotBlank
    @Column(nullable = false)
    private String clientIp;

    @NotBlank
    @Column(nullable = false)
    private String userAgent;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    public Travel(String destiny, LocalDateTime endDate, String clientIp, String userAgent, Card card) {
        this.destiny = destiny;
        this.endDate = endDate;
        this.clientIp = clientIp;
        this.userAgent = userAgent;
        this.card = card;
    }

    public String getDestiny() {
        return destiny;
    }

    public String getEndDateTimeToString(DateTimeFormatter formatter) {
        return endDate.format(formatter);
    }

}
