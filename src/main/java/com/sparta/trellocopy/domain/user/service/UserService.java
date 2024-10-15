package com.sparta.trellocopy.domain.user.service;

import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.dto.request.WithdrawRequest;
import com.sparta.trellocopy.domain.user.dto.response.UserResponse;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.exception.InvalidPasswordException;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse findById(AuthUser authUser) {
        User user = userRepository.findByIdOrElseThrow(authUser.getId());

        return UserResponse.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Transactional
    public void withdraw(WithdrawRequest withdrawRequest, AuthUser authUser) {
        User user = userRepository.findByIdOrElseThrow(authUser.getId());

        if (!passwordEncoder.matches(withdrawRequest.getPassword(), authUser.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);
    }
}
