package com.sparta.trellocopy.domain.workspace.exception;

import com.sparta.trellocopy.domain.common.exception.BadRequestException;

public class WorkspaceBadRequestException extends BadRequestException {
    public WorkspaceBadRequestException(String message){
        super(message);
    }
}
