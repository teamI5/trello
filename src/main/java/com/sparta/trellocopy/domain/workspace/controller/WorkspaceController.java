package com.sparta.trellocopy.domain.workspace.controller;

import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.workspace.dto.WorkspaceRequest;
import com.sparta.trellocopy.domain.workspace.dto.WorkspaceResponse;
import com.sparta.trellocopy.domain.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workSpaceService;

    @PostMapping
    public ResponseEntity<WorkspaceResponse> saveWorkSpace(
            @RequestBody WorkspaceRequest workSpaceRequest,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        return ResponseEntity.ok(workSpaceService.saveWorkspace(workSpaceRequest, authUser));
    }

    // 유저 초대하기
    @PutMapping("/{workspaceId}/{email}")
    public ResponseEntity<WorkspaceResponse> addUserAtWorkSpace(
            @PathVariable Long workspaceId,
            @PathVariable String email,
            @AuthenticationPrincipal AuthUser authUser
    ){
        return ResponseEntity.ok(workSpaceService.addUserAtWorkSpace(workspaceId, email, authUser));
    }

    // 자신의 모든 워크스페이스 조회하기
    @GetMapping
    public ResponseEntity<List<WorkspaceResponse>> getWorkSpace(
            @AuthenticationPrincipal AuthUser authUser
    ){
        return ResponseEntity.ok(workSpaceService.getWorkspace(authUser));
    }

    // 수정하기
    @PutMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponse> updateWorkSpace(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @RequestBody WorkspaceRequest workSpaceRequest
    ){
        return ResponseEntity.ok(workSpaceService.updateWorkspace(authUser, workspaceId, workSpaceRequest));
    }

    // 삭제하기
    @DeleteMapping("/{workspaceId}")
    public void deleteWorkSpace(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId
    ){
        workSpaceService.deleteWorkspace(authUser, workspaceId);
    }
}
