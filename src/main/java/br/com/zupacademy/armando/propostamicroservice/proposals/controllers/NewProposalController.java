package br.com.zupacademy.armando.propostamicroservice.proposals.controllers;

import br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler.ApiGenericException;
import br.com.zupacademy.armando.propostamicroservice.proposals.dtos.requests.NewProposalRequest;
import br.com.zupacademy.armando.propostamicroservice.proposals.entities.Proposal;
import br.com.zupacademy.armando.propostamicroservice.proposals.repository.ProposalRepository;
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

    private ProposalRepository proposalRepository;

    public NewProposalController(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    @PostMapping("/propostas")
    public ResponseEntity<?> newProposal(@RequestBody @Valid NewProposalRequest newProposalRequest,
                                         UriComponentsBuilder uriComponentsBuilder) throws ApiGenericException {
        Optional<Proposal> possibleProposal = proposalRepository.findByDocument(newProposalRequest.document);
        if (possibleProposal.isPresent()) {
            throw new ApiGenericException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe uma proposta para esse documento");
        }

        Proposal newProposal = newProposalRequest.toModel();
        proposalRepository.save(newProposal);
        URI path = uriComponentsBuilder.path("/propostas/{id}").build(newProposal.getId());
        return ResponseEntity.created(path).build();
    }

}
