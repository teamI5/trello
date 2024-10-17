package com.sparta.trellocopy.domain.card.repository;

import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.exception.CardNotFoundException;
import com.sparta.trellocopy.domain.user.entity.CardUser;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CardRepository extends JpaRepository<Card, Long> {

    default Card findByIdOrElseThrow(Long cardId) {
        return findById(cardId).orElseThrow(CardNotFoundException::new);
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    default Card findByIdOrElseThrowPessimistic(Long cardId) {
        return findById(cardId).orElseThrow(CardNotFoundException::new);
    }

    @Query("""
        SELECT c FROM Card c
        JOIN FETCH c.cardUsers cu
        JOIN FETCH cu.user
        JOIN c.lists l
        WHERE (l.board.id = :boardId)
        AND (:title IS NULL OR c.title LIKE CONCAT('%', :title, '%'))
        AND (:contents IS NULL OR c.contents LIKE CONCAT('%', :contents, '%'))
        AND (:startOfDay IS NULL OR c.deadline BETWEEN :startOfDay AND :endOfDay)
        AND (:email IS NULL OR cu.user.email = :name)
        ORDER BY c.modifiedAt DESC
        """)
    Page<Card> searchCards(Pageable pageable, Long boardId, String title, String contents, String email, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
