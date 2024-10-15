package com.sparta.trellocopy.domain.workspace.dto;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WorkspaceResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final List<Board> boards;
    //private final List<User> users;

    public WorkspaceResponse(Long id, String title, String description, List<Board> boards) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.boards = boards;
        // 워크스페이스가 가진 유저 목록 넣기
    }

    public static WorkspaceResponse fromWorkSpace(Workspace workSpace) {
        return new WorkspaceResponse(
                workSpace.getId(),
                workSpace.getTitle(),
                workSpace.getDescription(),
                workSpace.getBoards()
                // 워크스페이스가 가진 유저 목록 넣기
        );
    }
}
