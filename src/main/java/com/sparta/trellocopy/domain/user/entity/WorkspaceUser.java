package com.sparta.trellocopy.domain.user.entity;

import com.sparta.trellocopy.domain.workspace.entity.WorkSpace;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class WorkspaceUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private WorkSpace workspace;

    @Enumerated(EnumType.STRING)
    private WorkspaceRole role;

    @Builder
    private WorkspaceUser(User user, WorkSpace workspace, WorkspaceRole role) {
        this.user = user;
        this.workspace = workspace;
        this.role = role;
    }

    public void updateRole(WorkspaceRole role) {
        this.role = role;
    }
}
