package com.sparta.trellocopy.domain.user.entity;

import com.sparta.trellocopy.domain.common.exception.BadRequestException;

import java.util.Arrays;

public enum UserRole {
    ROLE_USER, ROLE_ADMIN;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("유효하지 않은 UerRole"));
    }
}
