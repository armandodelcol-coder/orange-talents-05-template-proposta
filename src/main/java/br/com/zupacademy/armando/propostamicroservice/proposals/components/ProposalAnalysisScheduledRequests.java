package br.com.zupacademy.armando.propostamicroservice.proposals.components;

import br.com.zupacademy.armando.propostamicroservice.core.feignclients.ProposalAnalysisClient;
import br.com.zupacademy.armando.propostamicroservice.proposals.dtos.request.ProposalAnalysisRequest;
import br.com.zupacademy.armando.propostamicroservice.proposals.dtos.response.ProposalAnalysisResponse;
import br.com.zupacademy.armando.propostamicroservice.proposals.entities.Proposal;
import br.com.zupacademy.armando.propostamicroservice.proposals.enums.ProposalStatus;
import br.com.zupacademy.armando.propostamicroservice.proposals.repository.ProposalRepository;
import br.com.zupacademy.armando.propostamicroservice.proposals.utils.ProposalStatusMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProposalAnalysisScheduledRequests {

    private final Logger logger = LoggerFactory.getLogger(ProposalAnalysisScheduledRequests.class);

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ProposalAnalysisClient proposalAnalysisClient;

    @Scheduled(fixedDelayString = "${delay.scheduled.proposal.analysis.requests}")
    void makeProposalAnalysisIfStatusIsNull() {
        List<Proposal> notEvaluatedProposals = proposalRepository.findByStatusIsNull();
        logger.info("Quantidade de propostas não avaliadas: " + notEvaluatedProposals.size());
        logger.info("Enviando analise de propostas");
        notEvaluatedProposals.forEach(proposal -> {
            try {
                ProposalAnalysisRequest proposalAnalysisRequest = new ProposalAnalysisRequest(
                        proposal.getDocument(),
                        proposal.getName(),
                        proposal.getId().toString()
                );
                ProposalAnalysisResponse proposalAnalysisResponse = proposalAnalysisClient.requestAnalysis(proposalAnalysisRequest);
                proposal.setStatus(ProposalStatusMapper.statusMap.get(proposalAnalysisResponse.getResultadoSolicitacao()));
            }
            catch (FeignException.UnprocessableEntity exception) {
                logger.warn("Proposta foi analisada com restrição.");
                proposal.setStatus(ProposalStatus.NAO_ELEGIVEL);
            }
            catch (FeignException exception) {
                logger.error("Problemas no envio de analise de proposta.");
            }
            proposalRepository.save(proposal);
        });
    }

}
