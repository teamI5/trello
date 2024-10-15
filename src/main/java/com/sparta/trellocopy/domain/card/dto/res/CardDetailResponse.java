package com.sparta.trellocopy.domain.card.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardDetailResponse {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime deadline;
    private String file_url;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
