package com.sparta.trellocopy.domain.user.controller;

import com.sparta.trellocopy.domain.user.dto.request.GrantRequest;
import com.sparta.trellocopy.domain.user.dto.request.LoginRequest;
import com.sparta.trellocopy.domain.user.dto.request.UserJoinRequest;
import com.sparta.trellocopy.domain.user.dto.response.LoginResponse;
import com.sparta.trellocopy.domain.user.dto.response.UserJoinResponse;
import com.sparta.trellocopy.domain.user.dto.response.UserResponse;
import com.sparta.trellocopy.domain.user.dto.response.WorkspaceUserResponse;
import com.sparta.trellocopy.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        UserJoinResponse userJoinResponse = authService.join(userJoinRequest);
        return ResponseEntity.ok(userJoinResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/grant")
    public ResponseEntity<WorkspaceUserResponse> grantWorkspace(@RequestBody GrantRequest grantRequest) {
        WorkspaceUserResponse workspaceUserResponse = authService.grant(grantRequest);
        return ResponseEntity.ok(workspaceUserResponse);
    }
}
