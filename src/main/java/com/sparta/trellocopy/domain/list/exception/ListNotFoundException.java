package com.sparta.trellocopy.domain.list.exception;

import com.sparta.trellocopy.domain.common.exception.NotFoundException;

public class ListNotFoundException extends NotFoundException {
    private static final String MESSAGE = "해당하는 리스트 정보를 찾을 수 없습니다.";

    public ListNotFoundException() {super(MESSAGE);}

    public ListNotFoundException(String message) {super(message);}
}
