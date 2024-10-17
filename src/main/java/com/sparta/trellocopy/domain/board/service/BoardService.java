package com.sparta.trellocopy.domain.board.service;

import com.sparta.trellocopy.domain.board.dto.BoardRequest;
import com.sparta.trellocopy.domain.board.dto.BoardResponse;
import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.board.exception.BoardNotFoundException;
import com.sparta.trellocopy.domain.board.repository.BoardRepository;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.WorkspaceRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.user.exception.WorkspaceRoleForbiddenException;
import com.sparta.trellocopy.domain.user.exception.WorkspaceUserNotFoundException;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import com.sparta.trellocopy.domain.workspace.exception.WorkspaceNotFoundException;
import com.sparta.trellocopy.domain.workspace.repository.WorkspaceRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final WorkspaceRepository workspaceRepository;

    // 보드 만들기
    @Transactional
    public BoardResponse saveBoard(BoardRequest boardRequest, Long workspaceId, AuthUser authUser) {

        Workspace workspace = workspaceRepository.findById(workspaceId)
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

    // 자신이 속해있는 특정 워크스페이스의 보드 전부 조회
    @Transactional(readOnly = true)
    public List<BoardResponse> getBoards(AuthUser authUser, Long workspaceId) {

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException("워크스페이스가 존재하지 않습니다."));

        // 속한 워크스페이스가 맞는지 확인
        WorkspaceUser wu = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, authUser.getId())
                .orElseThrow(()-> new WorkspaceUserNotFoundException("해당 워크스페이스에 속해 있지 않습니다."));

        List<Board> boardList = boardRepository.findByWorkspace_Id(workspace.getId());

        List<BoardResponse> boardResponseList = new ArrayList<>();
        for (Board board : boardList) {
            boardResponseList.add(BoardResponse.fromBoard(board));
        }

        return boardResponseList;
    }

    //자신이 속해있는 워크스페이스의 보드 단건 조회
    @Transactional(readOnly = true)
    public BoardResponse getBoard(AuthUser authUser, Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new BoardNotFoundException("보드가 존재하지 않습니다."));

        WorkspaceUser wu = workspaceUserRepository.findByWorkspaceIdAndUserId(board.getWorkspace().getId(), authUser.getId())
                .orElseThrow(()-> new WorkspaceUserNotFoundException("해당 워크스페이스에 속해 있지 않습니다."));

        return BoardResponse.fromBoard(board);
    }

    // 보드 수정(이름, 배경색, 이미지)
    @Transactional
    public BoardResponse updateBoard(AuthUser authUser, Long boardId, BoardRequest boardRequest) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new BoardNotFoundException("보드가 존재하지 않습니다."));

        WorkspaceUser wu = workspaceUserRepository.findByWorkspaceIdAndUserId(board.getWorkspace().getId(), authUser.getId())
                .orElseThrow(()-> new WorkspaceUserNotFoundException("해당 워크스페이스에 속해 있지 않습니다."));

        board.update(boardRequest.getTitle(),
                boardRequest.getBackgroundColor(),
                boardRequest.getImageUrl()
        );

        return BoardResponse.fromBoard(board);
    }

    // 보드 수정 낙관적 락 예외 발생시 반복
    public BoardResponse updateBoardRepeat (AuthUser authUser, Long boardId, BoardRequest boardRequest){
        int tries = 0;
        int maxTry = 10;

        while (tries<maxTry){
            try {
                return updateBoard(authUser, boardId, boardRequest);
            }catch (OptimisticLockException e){
                tries++;
                try{
                    Thread.sleep(50);
                }catch (InterruptedException ex){
                    throw new RuntimeException();
                }
            }
        }
        throw new RuntimeException("사용자가 많아 수정이 지연되고 있습니다. 잠시 후 다시 시도해 주세요.");
    }

    // 보드 삭제
    @Transactional
    public void deleteBoard(AuthUser authUser, Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new BoardNotFoundException("보드가 존재하지 않습니다."));

        WorkspaceUser wu = workspaceUserRepository.findByWorkspaceIdAndUserId(board.getWorkspace().getId(), authUser.getId())
                .orElseThrow(()-> new WorkspaceUserNotFoundException("해당 워크스페이스에 속해 있지 않습니다."));

        if(wu.getRole().equals(WorkspaceRole.READ_ONLY)){
            throw new WorkspaceRoleForbiddenException("보드 삭제 권한이 없습니다.");
        }

        boardRepository.delete(board);
    }
}
