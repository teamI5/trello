package com.sparta.trellocopy.domain.card.entity;

import com.sparta.trellocopy.domain.list.entity.Lists;
import jakarta.persistence.*;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id",nullable = false)
    private Lists lists;
}
