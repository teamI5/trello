package com.sparta.trellocopy.domain.user.repository;

import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    default User findByIdOrElseThrow(Long userId) {
        return findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailIncludingWithdrawn(@Param("email") String email);
}
