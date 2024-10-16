package com.sparta.trellocopy.domain.card.dto.req;

import com.sparta.trellocopy.domain.user.entity.CardUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardSearchRequest {
    private String title;
    private String contents;
    private LocalDateTime deadline;
    private CardUser cardUser;
}
