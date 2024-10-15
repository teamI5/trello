package com.sparta.trellocopy.domain.user.entity;

import com.sparta.trellocopy.domain.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WorkspaceUser> workspaces;

    @Builder
    private User(String email, String password, UserRole role, Boolean deleted) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.deleted = deleted;
    }
}
