package com.sparta.trellocopy.domain.workspace.service;

import com.sparta.trellocopy.domain.workspace.dto.WorkSpaceRequest;
import com.sparta.trellocopy.domain.workspace.dto.WorkSpaceResponse;
import com.sparta.trellocopy.domain.workspace.repository.WorkSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkSpaceService {

    private final WorkSpaceRepository workSpaceRepository;

    @Transactional
    public WorkSpaceResponse saveWorkSpace(WorkSpaceRequest workSpaceRequest) {}
}
