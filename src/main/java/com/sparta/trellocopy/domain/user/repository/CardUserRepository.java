package com.sparta.trellocopy.domain.user.repository;

import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.user.entity.CardUser;
import com.sparta.trellocopy.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardUserRepository extends JpaRepository<CardUser, Long> {
    @Query("""
    SELECT cu FROM CardUser cu
    WHERE (cu.card.id = :cardId)
    AND (cu.user.email = :email)
    """)
    Optional<CardUser> findByCardIdAndUserEmail(Long cardId, String email);

    boolean existsByCardAndUser(Card card, User user);
}
