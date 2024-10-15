package com.sparta.trellocopy.domain.board.service;

import com.sparta.trellocopy.domain.board.dto.BoardRequest;
import com.sparta.trellocopy.domain.board.dto.BoardResponse;
import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.board.repository.BoardRepository;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.WorkspaceRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.user.exception.WorkspaceRoleForbiddenException;
import com.sparta.trellocopy.domain.user.exception.WorkspaceUserNotFoundException;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import com.sparta.trellocopy.domain.workspace.exception.WorkspaceNotFoundException;
import com.sparta.trellocopy.domain.workspace.repository.WorkSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final WorkSpaceRepository workSpaceRepository;

    // 보드 만들기
    public BoardResponse saveBoard(BoardRequest boardRequest, Long workspaceId, AuthUser authUser) {

        Workspace workspace = workSpaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException("워크스페이스가 존재하지 않습니다."));

        // 권한 예외 처리 만들기!
        // 워크스페이스아이디와 유저아이디로 권한 찾기
        // 찾은 권한을 대조해 읽기 전용 권한이면 예외처리
        WorkspaceUser wu = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, authUser.getId())
                .orElseThrow(()-> new WorkspaceUserNotFoundException("해당 워크스페이스에 속해 있지 않습니다."));

        if(wu.getRole().equals(WorkspaceRole.READ_ONLY)){
            throw new WorkspaceRoleForbiddenException("보드 생성 권한이 없습니다.");
        }

        Board newBoard = Board.builder()
                .title(boardRequest.getTitle())
                .backgroundColor(boardRequest.getBackgroundColor())
                .imageUrl(boardRequest.getImageUrl())
                .workspace(workspace)
                .build();

        boardRepository.save(newBoard);

        return BoardResponse.fromBoard(newBoard);
    }

    //자신이 속해있는 워크스페이스의 보드 단건 조회

    // 보드 수정(이름, 배경색, 이미지)

    // 보드 삭제
}
