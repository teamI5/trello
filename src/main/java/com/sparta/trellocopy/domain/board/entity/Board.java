package com.sparta.trellocopy.domain.board.entity;

import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String backgroundColor;

    private String imageUrl;

    @Version
    private int version;

    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    @Builder
    public Board(
            String title,
            String backgroundColor,
            String imageUrl,
            Workspace workspace
    ) {
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.imageUrl = imageUrl;
        this.workspace = workspace;
    }

    public void update(String title, String backgroundColor, String imageUrl) {
        if(title != null){
            this.title = title;
        }
        if(backgroundColor != null){
            this.backgroundColor = backgroundColor;
        }
        if(imageUrl != null){
            this.imageUrl = imageUrl;
        }
    }
}
