package com.sparta.trellocopy.domain.workspace.entity;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.common.entity.Timestamped;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Workspace extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
    private List<Board> boards;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
    private List<WorkspaceUser> users;

    @Builder
    public Workspace(
            String title,
            String description,
            List<Board> boards,
            List<WorkspaceUser> users
    ) {
        this.title = title;
        this.description = description;
        this.boards = boards;
        this.users = users;
    }

    public void update(String title, String description) {
        if (this.title != null) {
            this.title = title;
        }
        if (this.description != null) {
            this.description = description;
        }
    }
}
