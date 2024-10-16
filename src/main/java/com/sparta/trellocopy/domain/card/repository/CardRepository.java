package com.sparta.trellocopy.domain.card.repository;

import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.exception.CardNotFoundException;
import com.sparta.trellocopy.domain.user.entity.CardUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface CardRepository extends JpaRepository<Card, Long> {
    default Card findByIdOrElseThrow(Long cardId) {
        return findById(cardId).orElseThrow(CardNotFoundException::new);
    }

    @Query("""
            SELECT c FROM Card c
            WHERE (:title IS NULL OR c.title = :title)
            AND (:contents IS NULL OR c.contents = :contents)
            AND (:CardUser IS NULL OR c.contents = :CardUser)
            AND (:deadline IS NULL OR c.contents = :deadline)
            ORDER BY c.modifiedAt DESC
            """)
    Page<Card> searchCards(Pageable pageable, String title, String contents, CardUser CardUser, LocalDateTime deadline);

}
