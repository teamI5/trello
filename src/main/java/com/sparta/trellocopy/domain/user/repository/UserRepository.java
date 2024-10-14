package com.sparta.trellocopy.domain.user.repository;

import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findByIdOrElseThrow(Long userId) {
        return findById(userId).orElseThrow(UserNotFoundException::new);
    }

    User findByEmail(String email);
}
