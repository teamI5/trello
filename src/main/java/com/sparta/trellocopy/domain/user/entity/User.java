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

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE user_id = ?")
@SQLRestriction("deleted = false")
@Entity
@Table(
    name = "user",
    indexes = {
        @Index(name = "idx_email", columnList = "email")
    }
)
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ColumnDefault("false")
    private Boolean deleted;

    @Builder
    private User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.deleted = false;
    }
}
