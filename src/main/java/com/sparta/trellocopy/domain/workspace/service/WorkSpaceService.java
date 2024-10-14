package com.sparta.trellocopy.domain.workspace.service;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.entity.UserRole;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import com.sparta.trellocopy.domain.workspace.dto.WorkSpaceRequest;
import com.sparta.trellocopy.domain.workspace.dto.WorkSpaceResponse;
import com.sparta.trellocopy.domain.workspace.entity.WorkSpace;
import com.sparta.trellocopy.domain.workspace.entity.WorkSpaceUser;
import com.sparta.trellocopy.domain.workspace.exception.WorkSpaceForbiddenException;
import com.sparta.trellocopy.domain.workspace.exception.WorkSpaceNotFoundException;
import com.sparta.trellocopy.domain.workspace.repository.WorkSpaceRepository;
import com.sparta.trellocopy.domain.workspace.repository.WorkSpaceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkSpaceService {

    private final WorkSpaceRepository workSpaceRepository;
    private final WorkSpaceUserRepository workSpaceUserRepository;
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

        WorkSpaceUser workSpaceUser = WorkSpaceUser.builder()
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
            Long userId,
            AuthUser authUser
    ) {
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(()-> new WorkSpaceNotFoundException("해당 워크스페이스를 찾을 수 없습니다."));


        User user = userRepository.findByIdOrElseThrow(authUser.getId());

        if(!authUser.getUserRole().equals(UserRole.ROLE_ADMIN) && !workSpace.getUsers().stream().map(WorkSpaceUser::getUser).toList().contains(user)){
            throw new WorkSpaceForbiddenException("관리자 혹은 소속 인원만 워크스페이스에 다른 유저를 초대할 수 있습니다.");
        }

        User addedUser = userRepository.findByIdOrElseThrow(userId);

        WorkSpaceUser workSpaceUser = WorkSpaceUser.builder()
                .user(addedUser)
                .workSpace(workSpace)
                .build();

        workSpaceUserRepository.save(workSpaceUser);

        return WorkSpaceResponse.fromWorkSpace(workSpace);
    }
}
