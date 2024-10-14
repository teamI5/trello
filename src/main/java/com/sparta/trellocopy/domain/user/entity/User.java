package com.sparta.trellocopy.domain.user.entity;

import com.sparta.trellocopy.domain.common.entity.Timestamped;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends Timestamped {

    @Id @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private UserRole role;

    private Boolean deleted;

    @Builder
    private User(String email, String password, UserRole role, Boolean deleted) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.deleted = deleted;
    }

}
