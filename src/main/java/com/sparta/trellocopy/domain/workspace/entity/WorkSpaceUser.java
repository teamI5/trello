package com.sparta.trellocopy.domain.workspace.entity;

import com.sparta.trellocopy.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class WorkSpaceUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private WorkSpace workSpace;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private WorkSpaceUser(WorkSpace workSpace, User user) {
        this.workSpace = workSpace;
        this.user = user;
    }
}
