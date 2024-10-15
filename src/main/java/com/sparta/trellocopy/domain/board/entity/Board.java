package com.sparta.trellocopy.domain.board.entity;

import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import jakarta.persistence.*;

@Entity
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;
}
