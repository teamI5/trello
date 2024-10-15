package com.sparta.trellocopy.domain.workspace.service;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.entity.UserRole;
import com.sparta.trellocopy.domain.user.exception.UserNotFoundException;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import com.sparta.trellocopy.domain.workspace.dto.WorkspaceRequest;
import com.sparta.trellocopy.domain.workspace.dto.WorkspaceResponse;
import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.workspace.exception.WorkspaceForbiddenException;
import com.sparta.trellocopy.domain.workspace.exception.WorkspaceNotFoundException;
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
public class WorkspaceService {

    private final WorkSpaceRepository workSpaceRepository;
    private final WorkspaceUserRepository workSpaceUserRepository;
    private final UserRepository userRepository;

    // 워크스페이스 만들기
    @Transactional
    public WorkspaceResponse saveWorkSpace(WorkspaceRequest workSpaceRequest, AuthUser authUser) {

        User user = userRepository.findByIdOrElseThrow(authUser.getId());

        if(!authUser.getUserRole().equals(UserRole.ROLE_ADMIN)){
            throw new WorkspaceForbiddenException("관리자만 워크스페이스를 생성할 수 있습니다.");
        }

        List<Board> boards = new ArrayList<>();
        Workspace workSpace = Workspace.builder()
                .title(workSpaceRequest.getTitle())
                .description(workSpaceRequest.getDescription())
                .boards(boards)
                .build();

        WorkspaceUser workSpaceUser = WorkspaceUser.builder()
                .user(user)
                .workspace(workSpace)
                .build();

        workSpaceRepository.save(workSpace);
        workSpaceUserRepository.save(workSpaceUser);

        return WorkspaceResponse.fromWorkSpace(workSpace);
    }

    // 관리자 혹은 워크스페이스 소속 인원이 다른 유저를 초대하기
    @Transactional
    public WorkspaceResponse addUserAtWorkSpace(
            Long workSpaceId,
            String email,
            AuthUser authUser
    ) {
        Workspace workspace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(()-> new WorkspaceNotFoundException("해당 워크스페이스를 찾을 수 없습니다."));

        // 중간 테이블에 있는 권한으로 바꾸기
        /*if(!authUser.getUserRole().equals(UserRole.ROLE_ADMIN) && !workspace.getUsers().stream().map(workSpaceUser -> workSpaceUser.getUser().getId()).toList().contains(authUser.getId())){
            throw new WorkspaceForbiddenException("관리자 혹은 소속 인원만 워크스페이스에 다른 유저를 초대할 수 있습니다.");
        }*/

        User addedUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("잘못된 이메일이거나 해당 유저가 존재하지 않습니다."));

        WorkspaceUser workSpaceUser = WorkspaceUser.builder()
                .user(addedUser)
                .workspace(workspace)
                .build();

        workSpaceUserRepository.save(workSpaceUser);

        return WorkspaceResponse.fromWorkSpace(workspace);
    }

    // 로그인된 유저가 가입된 모든 워크스페이스 조회
    public List<WorkspaceResponse> getWorkSpace(AuthUser authUser) {

        List<Workspace> workspaces = workSpaceRepository.findByUsers_User_Id(authUser.getId());

        List<WorkspaceResponse> workSpaceRespons = new ArrayList<>();
        for(Workspace workSpace : workspaces){
            workSpaceRespons.add(WorkspaceResponse.fromWorkSpace(workSpace));
        }

        return workSpaceRespons;
    }

    // 제목과 설명 수정
    @Transactional
    public WorkspaceResponse updateWorkSpace(AuthUser authUser, Long workspaceId, WorkspaceRequest workSpaceRequest) {

        Workspace workSpace = workSpaceRepository.findById(workspaceId)
                .orElseThrow(()-> new WorkspaceNotFoundException("해당 워크스페이스를 찾을 수 없습니다."));

        workSpace.update(workSpaceRequest.getTitle(), workSpaceRequest.getDescription());

        return WorkspaceResponse.fromWorkSpace(workSpace);
    }

    // 삭제
    @Transactional
    public void deleteWorkSpace(AuthUser authUser, Long workspaceId) {

        if(!authUser.getUserRole().equals(UserRole.ROLE_ADMIN)){
            throw new WorkspaceForbiddenException("관리자만 워크스페이스를 삭제할 수 있습니다.");
        }

        Workspace workSpace = workSpaceRepository.findById(workspaceId)
                .orElseThrow(()-> new WorkspaceNotFoundException("해당 워크스페이스를 찾을 수 없습니다."));

        workSpaceRepository.delete(workSpace);
    }
}
