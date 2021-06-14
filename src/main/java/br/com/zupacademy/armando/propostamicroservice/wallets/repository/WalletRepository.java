package br.com.zupacademy.armando.propostamicroservice.wallets.repository;

import br.com.zupacademy.armando.propostamicroservice.wallets.entities.Wallet;
import br.com.zupacademy.armando.propostamicroservice.wallets.enums.WalletName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {

    Optional<Wallet> findByNameAndCardId(WalletName name, String cardId);

}
