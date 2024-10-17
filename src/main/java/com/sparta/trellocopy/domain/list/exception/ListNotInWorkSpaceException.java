package com.sparta.trellocopy.domain.list.exception;

public class ListNotInWorkSpaceException extends RuntimeException {
    private static final String MESSAGE = "해당 리스트는 해당 워크스페이스에 존재하지않습니다.";

    public ListNotInWorkSpaceException() {super(MESSAGE);}

    public ListNotInWorkSpaceException(String message) {super(message);}
}
