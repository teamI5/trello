package com.sparta.trellocopy.domain.user.repository;

import com.sparta.trellocopy.domain.user.entity.CardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardUserRepository extends JpaRepository<CardUser, Long> {

    @Query("SELECT cu FROM CardUser cu JOIN FETCH cu.user WHERE cu.card.id = :cardId")
    List<CardUser> findByCardId(@Param("cardId") Long cardId);
}
