package br.com.zupacademy.armando.propostamicroservice.proposals.controllers;

import br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler.ApiGenericException;
import br.com.zupacademy.armando.propostamicroservice.config.metrics.ProposalsMetrics;
import br.com.zupacademy.armando.propostamicroservice.proposals.dtos.response.ProposalDetailsResponse;
import br.com.zupacademy.armando.propostamicroservice.proposals.entities.Proposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@RestController
public class ProposalDetailsController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProposalsMetrics proposalsMetrics;

    @GetMapping("propostas/{idProposta}")
    public ResponseEntity<ProposalDetailsResponse> findProposalDetails(@PathVariable("idProposta") Long proposalId) throws ApiGenericException {
        final Long beginRequest = System.currentTimeMillis();

        Proposal proposal = entityManager.find(Proposal.class, proposalId);
        if (proposal == null) {
            throw new ApiGenericException(HttpStatus.NOT_FOUND, "Proposta não encontrada.");
        }

        final Long endRequest = System.currentTimeMillis();
        final Long durationRequest = endRequest - beginRequest;
        proposalsMetrics.timerProposalDetailsGetRecord(Duration.of(durationRequest, ChronoUnit.MILLIS));

        return ResponseEntity.ok(new ProposalDetailsResponse(proposal));
    }

}
