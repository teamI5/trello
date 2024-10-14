package com.sparta.trellocopy.domain.comment.Dto;

import java.time.LocalDateTime;

public class CommentSaveResponseDto {

    private String comment;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentSaveResponseDto(String comment, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.comment = comment;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
