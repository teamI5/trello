package com.sparta.trellocopy.domain.workspace.entity;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class WorkSpace extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
    private List<Board> boards;
}
