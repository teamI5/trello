package com.sparta.trellocopy.domain.user.entity;

import com.sparta.trellocopy.domain.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE user SET deleted = 'true' WHERE user_id = ?")
@SQLRestriction("status != 'WITHDRAWN'")
@Entity
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ColumnDefault("0")
    private Boolean deleted;

    @Builder
    private User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
