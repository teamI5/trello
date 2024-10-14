package com.sparta.trellocopy.domain.card.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardSaveRequest {
    private String title;
    private String contents;
    private LocalDateTime deadline;
    private String file_url;
}
