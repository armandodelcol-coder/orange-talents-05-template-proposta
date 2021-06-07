package br.com.zupacademy.armando.propostamicroservice.cards.dtos.accountsclient.response;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.cards.entities.CardDueDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CardResponse {

    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private BigDecimal limite;
    private DueDateResponse vencimento;
    private String idProposta;

    public CardResponse(String id,
                        LocalDateTime emitidoEm,
                        String titular,
                        BigDecimal limite,
                        DueDateResponse vencimento,
                        String idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.vencimento = vencimento;
        this.idProposta = idProposta;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public DueDateResponse getVencimento() {
        return vencimento;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public Card toModel() {
        return new Card(
                this.id,
                this.emitidoEm,
                this.titular,
                this.limite
        );
    }

    public CardDueDate getVencimentoToModel(Card card) {
        return new CardDueDate(
                this.vencimento.getId(),
                this.vencimento.getDia(),
                this.vencimento.getDataDeCriacao(),
                card
        );
    }

}
