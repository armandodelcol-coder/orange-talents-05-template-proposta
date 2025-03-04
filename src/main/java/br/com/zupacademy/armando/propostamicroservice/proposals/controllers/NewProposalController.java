package br.com.zupacademy.armando.propostamicroservice.proposals.controllers;

import br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler.ApiGenericException;
import br.com.zupacademy.armando.propostamicroservice.config.metrics.ProposalsMetrics;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.proposalanalysis.ProposalAnalysisClient;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.proposalanalysis.dtos.ProposalAnalysisRequest;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.proposalanalysis.dtos.ProposalAnalysisResponse;
import br.com.zupacademy.armando.propostamicroservice.core.utils.ProposalDocumentEncrypt;
import br.com.zupacademy.armando.propostamicroservice.proposals.dtos.request.NewProposalRequest;
import br.com.zupacademy.armando.propostamicroservice.proposals.entities.Proposal;
import br.com.zupacademy.armando.propostamicroservice.proposals.enums.ProposalStatus;
import br.com.zupacademy.armando.propostamicroservice.proposals.repository.ProposalRepository;
import br.com.zupacademy.armando.propostamicroservice.proposals.utils.ProposalStatusMapper;
import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;
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
    private ProposalsMetrics proposalsMetrics;
    private final Tracer tracer;

    public NewProposalController(ProposalRepository proposalRepository,
                                 ProposalAnalysisClient proposalAnalysisClient,
                                 ProposalsMetrics proposalsMetrics, Tracer tracer) {
        this.proposalRepository = proposalRepository;
        this.proposalAnalysisClient = proposalAnalysisClient;
        this.proposalsMetrics = proposalsMetrics;
        this.tracer = tracer;
    }

    @PostMapping("/propostas")
    public ResponseEntity<?> newProposal(@RequestBody @Valid NewProposalRequest newProposalRequest,
                                         UriComponentsBuilder uriComponentsBuilder) throws ApiGenericException {
        // Testando OpenTracaing tag customizada
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("client.id", "propostas-microservice");
        // Testando OpenTracaing baggage customizada
        activeSpan.setBaggageItem("client.id", "propostas-microservice");
        // Testando OpenTracing log customizado
        activeSpan.log("Testando Log");

        // Criar proposta
        Proposal proposal = newProposalCreated(newProposalRequest);
        // Fazer analise da proposta em uma api externa e Seta o status da proposta baseado na resposta da api externa
        requestProposalAnalysis(proposal);
        // Update na proposta
        proposalRepository.save(proposal);
        URI path = uriComponentsBuilder.path("/propostas/{id}").build(proposal.getId());
        return ResponseEntity.created(path).build();
    }

    private Proposal newProposalCreated(NewProposalRequest proposalRequest) throws ApiGenericException {
        Optional<Proposal> possibleProposal = proposalRepository.findByDocument(ProposalDocumentEncrypt.genSecureHash(proposalRequest.document));
        if (possibleProposal.isPresent()) {
            throw new ApiGenericException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe uma proposta para esse documento");
        }

        Proposal newProposal = proposalRequest.toModel();
        proposalRepository.save(newProposal);
        this.proposalsMetrics.incrementProposalsCreated();
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
