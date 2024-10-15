package com.sparta.trellocopy.domain.workspace.exception;

import com.sparta.trellocopy.domain.common.exception.ForbiddenException;

public class WorkspaceForbiddenException extends ForbiddenException {
    public WorkspaceForbiddenException(String message) {
        super(message);
    }
}
