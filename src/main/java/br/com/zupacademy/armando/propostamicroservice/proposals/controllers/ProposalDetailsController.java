package br.com.zupacademy.armando.propostamicroservice.proposals.controllers;

import br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler.ApiGenericException;
import br.com.zupacademy.armando.propostamicroservice.proposals.dtos.response.ProposalDetailsResponse;
import br.com.zupacademy.armando.propostamicroservice.proposals.entities.Proposal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
public class ProposalDetailsController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("propostas/{idProposta}")
    public ResponseEntity<ProposalDetailsResponse> findProposalDetails(@PathVariable("idProposta") Long proposalId) throws ApiGenericException {
        Proposal proposal = entityManager.find(Proposal.class, proposalId);
        if (proposal == null) {
            throw new ApiGenericException(HttpStatus.NOT_FOUND, "Proposta não encontrada.");
        }

        return ResponseEntity.ok(new ProposalDetailsResponse(proposal));
    }

}
