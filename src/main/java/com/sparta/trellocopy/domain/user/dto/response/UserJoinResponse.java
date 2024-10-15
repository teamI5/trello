package com.sparta.trellocopy.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserJoinResponse {

    private String token;

    @Builder
    private UserJoinResponse(String token) {this.token = token;}
}
