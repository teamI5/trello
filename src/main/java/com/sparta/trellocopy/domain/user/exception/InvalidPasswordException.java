package com.sparta.trellocopy.domain.user.exception;

import com.sparta.trellocopy.domain.common.exception.BadRequestException;

public class InvalidPasswordException extends BadRequestException {
    private static final String MESSAGE = "잘못된 비밀번호입니다.";

    public InvalidPasswordException() {super(MESSAGE);}

    public InvalidPasswordException(String message) {super(message);}
}
