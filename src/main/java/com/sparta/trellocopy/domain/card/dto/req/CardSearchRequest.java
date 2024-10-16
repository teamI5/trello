package com.sparta.trellocopy.domain.card.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardSearchRequest {
    private Long workSpaceId;
    private Long boardId;
    private String title;
    private String contents;
    private LocalDate deadline;
    private String email;
}
