package com.sparta.trellocopy.domain.workspace.exception;

import com.sparta.trellocopy.domain.common.exception.ForbiddenException;

public class WorkSpaceForbiddenException extends ForbiddenException {
    public WorkSpaceForbiddenException(String message) {
        super(message);
    }
}
