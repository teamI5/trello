package com.sparta.trellocopy.domain.user.exception;

import com.sparta.trellocopy.domain.common.exception.BadRequestException;

public class WithdrawnUserException extends BadRequestException {
    private static final String MESSAGE = "탈퇴한 사용자입니다.";

    public WithdrawnUserException() {super(MESSAGE);}

    public WithdrawnUserException(String message) {super(message);}
}
