package br.com.zupacademy.armando.propostamicroservice.cards.repositories;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.CardDueDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDueDateRepository extends JpaRepository<CardDueDate, String> {
}
