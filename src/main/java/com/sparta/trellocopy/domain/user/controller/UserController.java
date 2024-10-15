package com.sparta.trellocopy.domain.user.controller;

import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.dto.request.WithdrawRequest;
import com.sparta.trellocopy.domain.user.dto.response.UserResponse;
import com.sparta.trellocopy.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal AuthUser authUser) {
        UserResponse userResponse = userService.findById(authUser);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody WithdrawRequest withdrawRequest,
                                         @AuthenticationPrincipal AuthUser authUser) {
        userService.withdraw(withdrawRequest, authUser);
        return ResponseEntity.noContent().build();
    }

}
