package com.sparta.trellocopy.domain.list.service;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.board.exception.BoardNotFoundException;
import com.sparta.trellocopy.domain.board.repository.BoardRepository;
import com.sparta.trellocopy.domain.list.dto.request.ListSaveRequest;
import com.sparta.trellocopy.domain.list.dto.request.ListUpdateRequest;
import com.sparta.trellocopy.domain.list.dto.response.ListSaveResponse;
import com.sparta.trellocopy.domain.list.dto.response.ListUpdateResponse;
import com.sparta.trellocopy.domain.list.entity.Lists;
import com.sparta.trellocopy.domain.list.exception.ListNotFoundException;
import com.sparta.trellocopy.domain.list.repository.ListRepository;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.WorkspaceRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.user.exception.WorkspaceRoleForbiddenException;
import com.sparta.trellocopy.domain.user.exception.WorkspaceUserNotFoundException;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListService {

    private final ListRepository listRepository;
    private final BoardRepository boardRepository;
    private final WorkspaceUserRepository workspaceUserRepository;

    @Transactional
    public ListSaveResponse saveLists(AuthUser authUser, ListSaveRequest request, Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("보드를 찾을 수 없습니다."));
        Long workspaceId = board.getWorkspace().getId();

        WorkspaceUser wu = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, authUser.getId())
                .orElseThrow(WorkspaceUserNotFoundException::new);

        // 역할이 readOnly인 경우 Exception 처리
        if (wu.getRole().equals(WorkspaceRole.READ_ONLY)) {
            throw new WorkspaceRoleForbiddenException("리스트를 생성할 권한이 없습니다.");
        }
        long orderNumbercount = listRepository.count();
        long orderNumber = orderNumbercount + 1;


        Lists newList = new Lists(
                request.getTitle(),
                board,
                orderNumber
        );
        Lists saveList = listRepository.save(newList);

        return new ListSaveResponse(
                saveList.getTitle()
        );

    }

    @Transactional
    public List<ListUpdateResponse> updateOrderNumbers(AuthUser authUser, ListUpdateRequest request, Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("보드를 찾을 수 없습니다."));
        Long workspaceId = board.getWorkspace().getId();

        WorkspaceUser wu = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, authUser.getId())
                .orElseThrow(() -> new WorkspaceUserNotFoundException("해당 워크스페이스에 있는 유저가 아닙니다"));

        // 역할이 readOnly인 경우 Exception 처리
        if (wu.getRole().equals(WorkspaceRole.READ_ONLY)) {
            throw new WorkspaceRoleForbiddenException("리스트를 수정할 권한이 없습니다.");
        }

        List<Lists> listsList = listRepository.findAllByBoardId(boardId);

        Lists listToMove = listsList.stream()
                .filter(list -> list.getId().equals(request.getListId()))
                .findFirst()
                .orElseThrow(ListNotFoundException::new);

        listsList.remove(listToMove);

        int newPosition = Math.min(request.getNewPosition(), listsList.size());

        listsList.add(newPosition, listToMove);

        for (int i = 0; i < listsList.size(); i++) {
            Lists list = listsList.get(i);
            Long newOrderNumber = (long) (i + 1);
            if (!list.getOrderNumber().equals(newOrderNumber)) {
                list.update(newOrderNumber);
            }
        }

        return listsList.stream()
                .map(list -> new ListUpdateResponse(
                        list.getTitle(),
                        list.getId(),
                        list.getOrderNumber()
                )).collect(Collectors.toList());

    }

    @Transactional
    public void deleteLists(AuthUser authUser, Long listId) {

        Lists list = listRepository.findById(listId)
                .orElseThrow(ListNotFoundException::new);

        Long boardId = list.getBoard().getId();

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("보드를 찾을 수 없습니다."));

        Long workspaceId = board.getWorkspace().getId();

        WorkspaceUser wu = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, authUser.getId())
                .orElseThrow(() -> new WorkspaceUserNotFoundException("해당 워크스페이스에 있는 유저가 아닙니다"));

        // 역할이 readOnly인 경우 Exception 처리
        if (wu.getRole().equals(WorkspaceRole.READ_ONLY)) {
            throw new WorkspaceRoleForbiddenException("리스트를 삭제할 권한이 없습니다.");
        }

        listRepository.deleteById(listId);
    }
}
