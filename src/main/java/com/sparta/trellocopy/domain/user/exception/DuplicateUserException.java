package com.sparta.trellocopy.domain.user.exception;

import com.sparta.trellocopy.domain.common.exception.BadRequestException;

public class DuplicateUserException extends BadRequestException {
    private static final String MESSAGE = "중복된 사용자입니다.";

    public DuplicateUserException() {super(MESSAGE);}

    public DuplicateUserException(String message) {super(message);}
}
