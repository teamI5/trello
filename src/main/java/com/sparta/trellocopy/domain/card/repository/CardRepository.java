package com.sparta.trellocopy.domain.card.repository;

import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.exception.CardNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    default Card findByIdOrElseThrow(Long cardId) {
        return findById(cardId).orElseThrow(CardNotFoundException::new);
    }
}
