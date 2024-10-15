package com.sparta.trellocopy.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    private String token;

    @Builder
    private LoginResponse(String token) {this.token = token;}
}
