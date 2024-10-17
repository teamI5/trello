package com.sparta.trellocopy.domain.user.exception;

public class CardUserAlreadyExistsException extends RuntimeException{
    private static final String MESSAGE = "이 사용자는 이미 이 카드에 담당자로 추가되어 있습니다.";

    public CardUserAlreadyExistsException() {
        super(MESSAGE);
    }

    public CardUserAlreadyExistsException(String message) {
        super(message);
    }
}
