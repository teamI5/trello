package com.sparta.trellocopy.domain.workspace.controller;

import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.workspace.dto.WorkSpaceRequest;
import com.sparta.trellocopy.domain.workspace.dto.WorkSpaceResponse;
import com.sparta.trellocopy.domain.workspace.service.WorkSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorkSpaceController {

    private final WorkSpaceService workSpaceService;

    @PostMapping("/workspaces")
    public ResponseEntity<WorkSpaceResponse> saveWorkSpace(
            @RequestBody WorkSpaceRequest workSpaceRequest,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        return ResponseEntity.ok(workSpaceService.saveWorkSpace(workSpaceRequest, authUser));
    }

    // 유저 초대하기

    // 조회하기

    // 수정하기

    // 삭제하기
}
