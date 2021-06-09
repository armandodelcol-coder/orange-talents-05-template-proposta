package br.com.zupacademy.armando.propostamicroservice.cards.repository;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {

    Optional<Card> findByIdentifierCode(String code);

}
