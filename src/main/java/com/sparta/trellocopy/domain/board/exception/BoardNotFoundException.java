package com.sparta.trellocopy.domain.board.exception;

import com.sparta.trellocopy.domain.common.exception.NotFoundException;

public class BoardNotFoundException extends NotFoundException {
    public BoardNotFoundException(String message) {
        super(message);
    }
}
