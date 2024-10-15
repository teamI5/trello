package com.sparta.trellocopy.domain.user.dto.request;

import lombok.Getter;

@Getter
public class GrantRequest {

    private Long workspaceId;

    private Long userId;

    private String role;
}
