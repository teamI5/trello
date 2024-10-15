package com.sparta.trellocopy.domain.comment.Dto;

import java.time.LocalDateTime;

public class CommentSaveResponseDto {

    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentSaveResponseDto(String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
