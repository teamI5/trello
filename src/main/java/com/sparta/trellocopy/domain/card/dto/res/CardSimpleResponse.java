package com.sparta.trellocopy.domain.card.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardSimpleResponse {
    private String message;
    private String email;
    private Integer status;

}
