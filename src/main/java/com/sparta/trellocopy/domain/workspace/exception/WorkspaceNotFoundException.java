package com.sparta.trellocopy.domain.workspace.exception;

import com.sparta.trellocopy.domain.common.exception.NotFoundException;

public class WorkspaceNotFoundException extends NotFoundException {
    public WorkspaceNotFoundException(String message) {
        super(message);
    }
}
