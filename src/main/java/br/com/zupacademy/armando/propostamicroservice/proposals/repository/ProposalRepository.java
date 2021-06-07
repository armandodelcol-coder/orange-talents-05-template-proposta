package br.com.zupacademy.armando.propostamicroservice.proposals.repository;

import br.com.zupacademy.armando.propostamicroservice.proposals.entities.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    Optional<Proposal> findByDocument(String document);

    @Query("select p from Proposal p where p.status = 'ELEGIVEL' and p.card is null")
    List<Proposal> findEligible();

}
