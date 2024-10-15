package com.sparta.trellocopy.domain.user.exception;

import com.sparta.trellocopy.domain.common.exception.NotFoundException;

public class WorkspaceUserNotFoundException extends NotFoundException {
    private static final String MESSAGE = "워크스페이스 혹은 사용자를 찾을 수 없습니다";

    public WorkspaceUserNotFoundException() {super(MESSAGE);}

    public WorkspaceUserNotFoundException(String message) {super(message);}
}
