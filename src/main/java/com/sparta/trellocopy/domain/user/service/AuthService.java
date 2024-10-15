package com.sparta.trellocopy.domain.user.service;

import com.sparta.trellocopy.config.JwtUtil;
import com.sparta.trellocopy.domain.common.exception.BadRequestException;
import com.sparta.trellocopy.domain.user.dto.request.LoginRequest;
import com.sparta.trellocopy.domain.user.dto.request.UserJoinRequest;
import com.sparta.trellocopy.domain.user.dto.response.LoginResponse;
import com.sparta.trellocopy.domain.user.dto.response.UserJoinResponse;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.entity.UserRole;
import com.sparta.trellocopy.domain.user.exception.DuplicateUserException;
import com.sparta.trellocopy.domain.user.exception.InvalidPasswordException;
import com.sparta.trellocopy.domain.user.exception.UserNotFoundException;
import com.sparta.trellocopy.domain.user.exception.WithdrawnUserException;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final UserRepository userRepository;
    //    private final WorkSpaceRepository workSpaceRepository;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    @Transactional
    public UserJoinResponse join(UserJoinRequest userJoinRequest) {

        String email = userJoinRequest.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("이미 존재하는 이메일입니다.");
        }

        Optional<User> optionalUser = userRepository.findByEmailIncludingWithdrawn(email);
        if (optionalUser.isPresent()) {
            if (optionalUser.get().getDeleted()) {
                throw new WithdrawnUserException("탈퇴한 사용자의 아이디는 재사용할 수 없습니다.");
            }
            throw new DuplicateUserException();
        }

        String encodedPassword = passwordEncoder.encode(userJoinRequest.getPassword());
        UserRole role = UserRole.of(userJoinRequest.getRole());

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .role(role)
                .build();

        userRepository.save(user);

        String token = jwtUtil.createToken(user);

        return UserJoinResponse.builder().token(token).build();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UserNotFoundException("가입되지 않은 사용자입니다."));

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(user);

        return LoginResponse.builder().token(token).build();
    }

//    @Transactional
//    public WorkspaceUserResponse grant(GrantRequest grantRequest) {
//        User user = userRepository.findById(grantRequest.getUserId()).orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
////        WorkSpace workSpace = workSpaceRepository.findById(grantRequest.getWorkspaceId()).orElseThrow(() -> new NotFoundException("존재하지 않는 워크스페이스입니다."));
//        WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(user.getId(), workSpace.getId()).orElseThrow(WorkspaceUserNotFoundException::new);
//        workspaceUser.updateRole(WorkspaceRole.of(grantRequest.getRole()));
//
//        return WorkspaceUserResponse.builder()
////                .workspaceId(workSpace.getId())
//                .email(user.getEmail())
////                .workspaceRole(workspaceUser.getRole())
//                .build();
//    }
}
