package com.sparta.trellocopy.domain.workspace.service;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.entity.UserRole;
import com.sparta.trellocopy.domain.user.exception.UserNotFoundException;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import com.sparta.trellocopy.domain.workspace.dto.WorkSpaceRequest;
import com.sparta.trellocopy.domain.workspace.dto.WorkSpaceResponse;
import com.sparta.trellocopy.domain.workspace.entity.WorkSpace;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.workspace.exception.WorkSpaceForbiddenException;
import com.sparta.trellocopy.domain.workspace.exception.WorkSpaceNotFoundException;
import com.sparta.trellocopy.domain.workspace.repository.WorkSpaceRepository;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkSpaceService {

    private final WorkSpaceRepository workSpaceRepository;
    private final WorkspaceUserRepository workSpaceUserRepository;
    private final UserRepository userRepository;

    // 워크스페이스 만들기
    @Transactional
    public WorkSpaceResponse saveWorkSpace(WorkSpaceRequest workSpaceRequest, AuthUser authUser) {

        User user = userRepository.findByIdOrElseThrow(authUser.getId());

        if(!authUser.getUserRole().equals(UserRole.ROLE_ADMIN)){
            throw new WorkSpaceForbiddenException("관리자만 워크스페이스를 생성할 수 있습니다.");
        }

        List<Board> boards = new ArrayList<>();
        WorkSpace workSpace = WorkSpace.builder()
                .title(workSpaceRequest.getTitle())
                .description(workSpaceRequest.getDescription())
                .boards(boards)
                .build();

        WorkspaceUser workSpaceUser = WorkspaceUser.builder()
                .user(user)
                .workSpace(workSpace)
                .build();

        workSpaceRepository.save(workSpace);
        workSpaceUserRepository.save(workSpaceUser);

        return WorkSpaceResponse.fromWorkSpace(workSpace);
    }

    // 관리자 혹은 워크스페이스 소속 인원이 다른 유저를 초대하기
    @Transactional
    public WorkSpaceResponse addUserAtWorkSpace(
            Long workSpaceId,
            String email,
            AuthUser authUser
    ) {
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(()-> new WorkSpaceNotFoundException("해당 워크스페이스를 찾을 수 없습니다."));

        if(!authUser.getUserRole().equals(UserRole.ROLE_ADMIN) && !workSpace.getUsers().stream().map(workSpaceUser -> workSpaceUser.getUser().getId()).toList().contains(authUser.getId())){
            throw new WorkSpaceForbiddenException("관리자 혹은 소속 인원만 워크스페이스에 다른 유저를 초대할 수 있습니다.");
        }

        User addedUser = userRepository.findByEmail(email);
        if(addedUser == null){
            throw new UserNotFoundException("잘못된 이메일이거나 해당 유저가 존재하지 않습니다.");
        }

        WorkspaceUser workSpaceUser = WorkspaceUser.builder()
                .user(addedUser)
                .workSpace(workSpace)
                .build();

        workSpaceUserRepository.save(workSpaceUser);

        return WorkSpaceResponse.fromWorkSpace(workSpace);
    }

    // 로그인된 유저가 가입된 모든 워크스페이스 조회
    public List<WorkSpaceResponse> getWorkSpace(AuthUser authUser) {

        List<WorkSpace> workSpaces = workSpaceRepository.findByUsers_User_Id(authUser.getId());

        List<WorkSpaceResponse> workSpaceResponses = new ArrayList<>();
        for(WorkSpace workSpace : workSpaces){
            workSpaceResponses.add(WorkSpaceResponse.fromWorkSpace(workSpace));
        }

        return workSpaceResponses;
    }

    // 제목과 설명 수정
    @Transactional
    public WorkSpaceResponse updateWorkSpace(AuthUser authUser, Long workspaceId, WorkSpaceRequest workSpaceRequest) {

        WorkSpace workSpace = workSpaceRepository.findById(workspaceId)
                .orElseThrow(()-> new WorkSpaceNotFoundException("해당 워크스페이스를 찾을 수 없습니다."));

        workSpace.update(workSpaceRequest.getTitle(), workSpaceRequest.getDescription());

        return WorkSpaceResponse.fromWorkSpace(workSpace);
    }

    // 삭제
    @Transactional
    public void deleteWorkSpace(AuthUser authUser, Long workspaceId) {

        if(!authUser.getUserRole().equals(UserRole.ROLE_ADMIN)){
            throw new WorkSpaceForbiddenException("관리자만 워크스페이스를 삭제할 수 있습니다.");
        }

        WorkSpace workSpace = workSpaceRepository.findById(workspaceId)
                .orElseThrow(()-> new WorkSpaceNotFoundException("해당 워크스페이스를 찾을 수 없습니다."));

        workSpaceRepository.delete(workSpace);
    }
}
