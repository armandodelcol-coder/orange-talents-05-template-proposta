package br.com.zupacademy.armando.propostamicroservice.cards.components;

import br.com.zupacademy.armando.propostamicroservice.cards.dtos.accountsclient.response.CardResponse;
import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.AccountsClient;
import br.com.zupacademy.armando.propostamicroservice.proposals.entities.Proposal;
import br.com.zupacademy.armando.propostamicroservice.proposals.repository.ProposalRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardScheduledRequests {

    private final Logger logger = LoggerFactory.getLogger(CardScheduledRequests.class);

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private AccountsClient accountsClient;

    @Autowired
    private CreateCard createCard;

    @Scheduled(fixedDelayString = "${delay.scheduled.card.requests}")
    void searchAndCardsAssociation() {
        List<Proposal> approvedProposals = proposalRepository.findEligible();
        logger.info("Quantidade de propostas elegíveis: " + approvedProposals.size());
        logger.info("Verificando cartões de propostas elegíveis");
        approvedProposals.forEach(proposal -> {
            try {
                CardResponse cardResponse = accountsClient.getByProposalId(proposal.getId());
                Card newCard = createCard.newCard(cardResponse);
                proposal.setCard(newCard);
                proposalRepository.save(proposal);
            } catch (FeignException exception) {
                logger.error(exception.getMessage());
                logger.warn(exception.request().toString());
                logger.warn(exception.request().url());
            }
        });
    }

}
