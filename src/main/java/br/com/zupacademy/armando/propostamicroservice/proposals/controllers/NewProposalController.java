package br.com.zupacademy.armando.propostamicroservice.proposals.controllers;

import br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler.ApiGenericException;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.ProposalAnalysisClient;
import br.com.zupacademy.armando.propostamicroservice.proposals.dtos.request.ProposalAnalysisRequest;
import br.com.zupacademy.armando.propostamicroservice.proposals.dtos.response.ProposalAnalysisResponse;
import br.com.zupacademy.armando.propostamicroservice.proposals.dtos.request.NewProposalRequest;
import br.com.zupacademy.armando.propostamicroservice.proposals.entities.Proposal;
import br.com.zupacademy.armando.propostamicroservice.proposals.enums.ProposalStatus;
import br.com.zupacademy.armando.propostamicroservice.proposals.repository.ProposalRepository;
import br.com.zupacademy.armando.propostamicroservice.proposals.utils.ProposalStatusMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class NewProposalController {

    private final Logger logger = LoggerFactory.getLogger(NewProposalController.class);

    private ProposalRepository proposalRepository;
    private ProposalAnalysisClient proposalAnalysisClient;

    public NewProposalController(ProposalRepository proposalRepository,
                                 ProposalAnalysisClient proposalAnalysisClient) {
        this.proposalRepository = proposalRepository;
        this.proposalAnalysisClient = proposalAnalysisClient;
    }

    @PostMapping("/propostas")
    public ResponseEntity<?> newProposal(@RequestBody @Valid NewProposalRequest newProposalRequest,
                                         UriComponentsBuilder uriComponentsBuilder) throws ApiGenericException {
        // Criar proposta
        Proposal proposal = newProposalCreated(newProposalRequest);
        // Fazer analise da proposta em uma api externa e Seta o status da proposta baseado na resposta da api externa
        requestProposalAnalysis(proposal);
        // Update na proposta
        proposalRepository.save(proposal);
        URI path = uriComponentsBuilder.path("/propostas/{id}").build(proposal.getId());
        System.out.println("REALMENTE BUILDOU");
        return ResponseEntity.created(path).build();
    }

    private Proposal newProposalCreated(NewProposalRequest proposalRequest) throws ApiGenericException {
        Optional<Proposal> possibleProposal = proposalRepository.findByDocument(proposalRequest.document);
        if (possibleProposal.isPresent()) {
            throw new ApiGenericException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe uma proposta para esse documento");
        }

        Proposal newProposal = proposalRequest.toModel();
        proposalRepository.save(newProposal);
        return newProposal;
    }

    private void requestProposalAnalysis(Proposal proposal) {
        logger.info("Cliente vai ser analisado pelo serviço externo de analise de proposta.");
        ProposalAnalysisRequest proposalAnalysisRequest = new ProposalAnalysisRequest(
          proposal.getDocument(),
          proposal.getName(),
          proposal.getId().toString()
        );
        try {
            ProposalAnalysisResponse proposalAnalysisResponse = proposalAnalysisClient.requestAnalysis(proposalAnalysisRequest);
            proposal.setStatus(ProposalStatusMapper.statusMap.get(proposalAnalysisResponse.getResultadoSolicitacao()));
        }
        catch (FeignException.UnprocessableEntity exception) {
            logger.warn("Proposta foi analisada com restrição.");
            proposal.setStatus(ProposalStatus.NAO_ELEGIVEL);
        }
        catch (FeignException exception) {
            logger.warn("Tivemos um problema ao analisar a proposta do cliente, entre em contato com o administrador.");
            /* throw new ApiGenericException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Tivemos um problema ao analisar a proposta do cliente, entre em contato com o administrador."
            ); */
        }
    }

}
