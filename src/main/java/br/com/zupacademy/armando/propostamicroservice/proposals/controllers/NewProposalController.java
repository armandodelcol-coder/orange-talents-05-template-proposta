package br.com.zupacademy.armando.propostamicroservice.proposals.controllers;

import br.com.zupacademy.armando.propostamicroservice.proposals.dtos.requests.NewProposalRequest;
import br.com.zupacademy.armando.propostamicroservice.proposals.entities.Proposal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class NewProposalController {

    private EntityManager entityManager;

    public NewProposalController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/propostas")
    @Transactional
    public ResponseEntity<?> newProposal(@RequestBody @Valid NewProposalRequest newProposalRequest,
                                         UriComponentsBuilder uriComponentsBuilder) {
        Proposal newProposal = newProposalRequest.toModel();
        entityManager.persist(newProposal);
        URI path = uriComponentsBuilder.path("/propostas/{id}").build(newProposal.getId());
        return ResponseEntity.created(path).build();
    }

}
