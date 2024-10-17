package com.sparta.trellocopy.domain.user.exception;

public class CardUserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당 사용자는 해당 카드의 담당자가 아닙니다.";

    public CardUserNotFoundException() {
        super(MESSAGE);
    }
    public CardUserNotFoundException(String message) {
        super(message);
    }
}