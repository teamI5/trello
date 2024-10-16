package com.sparta.trellocopy.domain.card.exception;

import com.sparta.trellocopy.domain.common.exception.ForbiddenException;

public class CardForbiddenException extends ForbiddenException {
    private static final String MESSAGE = "권한을 가져야 카드를 생성할 수 있습니다.";

    public CardForbiddenException(){
        super(MESSAGE);
    }

    public CardForbiddenException(String message){
        super(message);
    }
}
