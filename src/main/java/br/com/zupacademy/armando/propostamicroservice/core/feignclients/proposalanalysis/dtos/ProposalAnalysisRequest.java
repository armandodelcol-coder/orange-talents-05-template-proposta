package br.com.zupacademy.armando.propostamicroservice.core.feignclients.proposalanalysis.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class ProposalAnalysisRequest {

    @JsonProperty
    private String documento;
    @JsonProperty
    private String nome;
    @JsonProperty
    private String idProposta;

    @Deprecated
    public ProposalAnalysisRequest() {
    }

    public ProposalAnalysisRequest(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

}
