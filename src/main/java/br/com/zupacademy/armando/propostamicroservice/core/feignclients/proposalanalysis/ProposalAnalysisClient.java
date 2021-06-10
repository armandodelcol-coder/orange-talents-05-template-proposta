package br.com.zupacademy.armando.propostamicroservice.core.feignclients.proposalanalysis;

import br.com.zupacademy.armando.propostamicroservice.core.feignclients.proposalanalysis.dtos.ProposalAnalysisRequest;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.proposalanalysis.dtos.ProposalAnalysisResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "proposal-analysis", url = "${service.proposal.analysis}")
public interface ProposalAnalysisClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/solicitacao", consumes = "application/json", produces = "application/json")
    ProposalAnalysisResponse requestAnalysis(ProposalAnalysisRequest proposalAnalysisRequest);

}
