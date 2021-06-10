package br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos;

import java.time.LocalDateTime;

public class DueDateResponse {

    private String id;
    private Integer dia;
    private LocalDateTime dataDeCriacao;

    public DueDateResponse(String id, Integer dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return id;
    }

    public Integer getDia() {
        return dia;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

}
