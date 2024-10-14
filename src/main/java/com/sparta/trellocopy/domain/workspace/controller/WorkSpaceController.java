package com.sparta.trellocopy.domain.workspace.controller;

import com.sparta.trellocopy.domain.workspace.dto.WorkSpaceRequest;
import com.sparta.trellocopy.domain.workspace.dto.WorkSpaceResponse;
import com.sparta.trellocopy.domain.workspace.service.WorkSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorkSpaceController {

    private final WorkSpaceService workSpaceService;

    @PostMapping("/workspaces")
    public ResponseEntity<WorkSpaceResponse> saveWorkSpace(@RequestBody WorkSpaceRequest workSpaceRequest) {
        return ResponseEntity.ok(workSpaceService)
    }
}
