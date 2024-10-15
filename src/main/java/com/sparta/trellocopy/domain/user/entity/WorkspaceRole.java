package com.sparta.trellocopy.domain.user.entity;

import com.sparta.trellocopy.domain.common.exception.BadRequestException;

import java.util.Arrays;

public enum WorkspaceRole {
    WORKSPACE, BOARD, READ_ONLY;

    public static WorkspaceRole of(String role) {
        return Arrays.stream(WorkspaceRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("유효하지 않은 WorkspaceRole"));
    }
}
