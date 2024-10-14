package com.sparta.trellocopy.domain.board.entity;

import jakarta.persistence.*;

@Entity
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
}
