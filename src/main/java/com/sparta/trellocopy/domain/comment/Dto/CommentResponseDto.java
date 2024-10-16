package com.sparta.trellocopy.domain.comment.Dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Long id, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
