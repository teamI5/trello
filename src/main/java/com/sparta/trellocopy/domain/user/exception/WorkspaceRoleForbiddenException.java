package com.sparta.trellocopy.domain.user.exception;

import com.sparta.trellocopy.domain.common.exception.ForbiddenException;

public class WorkspaceRoleForbiddenException extends ForbiddenException {
    public WorkspaceRoleForbiddenException(String message) {
        super(message);
    }
}
