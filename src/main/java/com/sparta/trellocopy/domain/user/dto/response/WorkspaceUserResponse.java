package com.sparta.trellocopy.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WorkspaceUserResponse {
    private Long workspaceId;
    private String email;
    private String workspaceRole;

    @Builder
    private WorkspaceUserResponse(Long workspaceId, String email, String workspaceRole) {
        this.workspaceId = workspaceId;
        this.email = email;
        this.workspaceRole = workspaceRole;
    }
}
