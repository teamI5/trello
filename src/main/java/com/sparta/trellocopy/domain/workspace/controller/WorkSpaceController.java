package com.sparta.trellocopy.domain.workspace.controller;

import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.workspace.dto.WorkSpaceRequest;
import com.sparta.trellocopy.domain.workspace.dto.WorkSpaceResponse;
import com.sparta.trellocopy.domain.workspace.service.WorkSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspaces")
public class WorkSpaceController {

    private final WorkSpaceService workSpaceService;

    @PostMapping
    public ResponseEntity<WorkSpaceResponse> saveWorkSpace(
            @RequestBody WorkSpaceRequest workSpaceRequest,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        return ResponseEntity.ok(workSpaceService.saveWorkSpace(workSpaceRequest, authUser));
    }

    // 유저 초대하기
    @PutMapping("/{workspaceId}/{userId}")
    public ResponseEntity<WorkSpaceResponse> addUserAtWorkSpace(
            @PathVariable Long workspaceId,
            @PathVariable Long userId,
            @AuthenticationPrincipal AuthUser authUser
    ){
        return ResponseEntity.ok(workSpaceService.addUserAtWorkSpace(workspaceId, userId, authUser));
    }

    // 자신의 모든 워크스페이스 조회하기
    @GetMapping
    public ResponseEntity<List<WorkSpaceResponse>> getWorkSpace(
            @AuthenticationPrincipal AuthUser authUser
    ){
        return ResponseEntity.ok(workSpaceService.getWorkSpace(authUser));
    }

    // 수정하기
    @PutMapping("/{workspaceId}")
    public ResponseEntity<WorkSpaceResponse> updateWorkSpace(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @RequestBody WorkSpaceRequest workSpaceRequest
    ){
        return ResponseEntity.ok(workSpaceService.updateWorkSpace(authUser, workspaceId, workSpaceRequest));
    }

    // 삭제하기
}
