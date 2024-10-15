package com.sparta.trellocopy.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private String email;

    private String role;

    @Builder
    private UserResponse(String email, String role) {
        this.email = email;
        this.role = role;
    }
}
