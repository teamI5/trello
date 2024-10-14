package com.sparta.trellocopy.domain.card.repository;

import com.sparta.trellocopy.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
