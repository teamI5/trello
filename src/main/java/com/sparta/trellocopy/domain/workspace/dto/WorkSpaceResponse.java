package com.sparta.trellocopy.domain.workspace.dto;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.workspace.entity.WorkSpace;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WorkSpaceResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final List<Board> boards;
    private final List<User> users;

    public WorkSpaceResponse(Long id, String title, String description, List<Board> boards, List<User> users) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.boards = boards;
        this.users = users;
    }

    public static WorkSpaceResponse fromWorkSpace(WorkSpace workSpace) {
        return new WorkSpaceResponse(
                workSpace.getId(),
                workSpace.getTitle(),
                workSpace.getDescription(),
                workSpace.getBoards(),
                workSpace.getUsers().stream().map(WorkspaceUser::getUser).collect(Collectors.toList())
                // 중간 테이블에서 유저 추출
        );
    }
}
