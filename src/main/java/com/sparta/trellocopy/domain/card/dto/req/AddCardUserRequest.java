package com.sparta.trellocopy.domain.card.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCardUserRequest {
    private Long workSpaceId;
    private String email;
}
