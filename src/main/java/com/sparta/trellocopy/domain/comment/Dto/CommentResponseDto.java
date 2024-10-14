package com.sparta.trellocopy.domain.comment.Dto;

import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;

    public CommentResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
