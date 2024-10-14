package com.sparta.trellocopy.domain.workspace.dto;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.workspace.entity.WorkSpace;
import lombok.Getter;

import java.util.List;

@Getter
public class WorkSpaceResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final List<Board> boards;

    public WorkSpaceResponse(Long id, String title, String description, List<Board> boards) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.boards = boards;
    }

    public static WorkSpaceResponse fromWorkSpace(WorkSpace workSpace) {
        return new WorkSpaceResponse(
                workSpace.getId(),
                workSpace.getTitle(),
                workSpace.getDescription(),
                workSpace.getBoards()
        );
    }
}
