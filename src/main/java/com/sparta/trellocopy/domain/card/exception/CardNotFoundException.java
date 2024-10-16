package com.sparta.trellocopy.domain.card.exception;

import com.sparta.trellocopy.domain.common.exception.NotFoundException;

public class CardNotFoundException extends NotFoundException {
    private static final String MESSAGE = "카드정보를 찾을 수 없습니다.";

    public CardNotFoundException() {super(MESSAGE);}

    public CardNotFoundException(String message) {super(message);}
}
