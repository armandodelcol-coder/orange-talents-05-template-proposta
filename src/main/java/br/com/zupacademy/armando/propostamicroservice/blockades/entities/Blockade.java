package br.com.zupacademy.armando.propostamicroservice.blockades.entities;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_blockades")
public class Blockade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String clientIp;

    @NotBlank
    @Column(nullable = false)
    private String userAgent;

    @CreationTimestamp
    @Column(columnDefinition = "datetime", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(nullable = false)
    private Boolean success = false;

    public Blockade(String clientIp, String userAgent, Card card) {
        this.clientIp = clientIp;
        this.userAgent = userAgent;
        this.card = card;
    }

    public void blockadeWithSuccess() {
        this.success = true;
    }

}
