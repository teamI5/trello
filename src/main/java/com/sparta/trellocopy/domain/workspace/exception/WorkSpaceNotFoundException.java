package com.sparta.trellocopy.domain.workspace.exception;

import com.sparta.trellocopy.domain.common.exception.NotFoundException;

public class WorkSpaceNotFoundException extends NotFoundException {
    public WorkSpaceNotFoundException(String message) {
        super(message);
    }
}
