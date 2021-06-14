package br.com.zupacademy.armando.propostamicroservice.wallets.controllers;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.cards.repository.CardRepository;
import br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler.ApiGenericException;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.AccountsClient;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos.NewWalletAccountsRequest;
import br.com.zupacademy.armando.propostamicroservice.travels.controllers.NewTravelController;
import br.com.zupacademy.armando.propostamicroservice.wallets.dtos.request.NewWalletRequest;
import br.com.zupacademy.armando.propostamicroservice.wallets.entities.Wallet;
import br.com.zupacademy.armando.propostamicroservice.wallets.enums.WalletName;
import br.com.zupacademy.armando.propostamicroservice.wallets.repository.WalletRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class NewWalletController {

    private final Logger logger = LoggerFactory.getLogger(NewTravelController.class);

    @Autowired
    private CardRepository cardRepository;
    
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private AccountsClient accountsClient;

    @PostMapping("/cartoes/{cardIdentifier}/carteiras")
    public ResponseEntity<?> newWallet(
            @PathVariable String cardIdentifier,
            @RequestBody @Valid NewWalletRequest newWalletRequest,
            UriComponentsBuilder uriComponentsBuilder
    ) throws ApiGenericException {
        Optional<Card> possibleCard = cardRepository.findByIdentifierCode(cardIdentifier);
        if (possibleCard.isEmpty()) throw new ApiGenericException(HttpStatus.NOT_FOUND, "Cartão não encontrado");

        Card card = possibleCard.get();
        verifyDuplicateWalletName(newWalletRequest, card);
        Wallet newWallet = newWalletRequest.toModel(card);
        registerWalletInAccounts(card, newWallet);
        walletRepository.save(newWallet);
        URI path = uriComponentsBuilder.path("/cartoes/{cardIdentifier}/carteiras/{id}").build(cardIdentifier, newWallet.getId());
        return ResponseEntity.created(path).build();
    }

    private void registerWalletInAccounts(Card card, Wallet newWallet) throws ApiGenericException {
        NewWalletAccountsRequest newWalletAccountsRequest = new NewWalletAccountsRequest(newWallet.getEmail(), newWallet.getName().toString());
        try {
            accountsClient.newWallet(card.getId(), newWalletAccountsRequest);
            logger.info("Carteira associada com sucesso");
        } catch (FeignException e) {
            logger.warn("Não foi possível associar a carteira");
            logger.info("Status retornado na api externa: " + e.status());
            logger.error(e.contentUTF8());
            throw new ApiGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível associar a carteira ao cartão informado, entre em contato com o administrador.");
        }
    }

    private void verifyDuplicateWalletName(NewWalletRequest newWalletRequest, Card card) throws ApiGenericException {
        Optional<Wallet> possibleWallet = walletRepository.findByNameAndCardId(WalletName.valueOf(newWalletRequest.name), card.getId());
        if (possibleWallet.isPresent()) throw new ApiGenericException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe uma carteira com esse nome para esse cartão.");
    }

}
