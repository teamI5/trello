package com.sparta.trellocopy.domain.board.dto;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import lombok.Getter;

@Getter
public class BoardResponse {

    private final Long id;

    private final String title;

    private final String backgroundColor;

    private final String imageUrl;

    private final String workspaceName;


    public BoardResponse(Long id, String title, String backgroundColor, String imageUrl, String workspaceName) {
        this.id = id;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.imageUrl = imageUrl;
        this.workspaceName = workspaceName;
    }

    public static BoardResponse fromBoard(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getBackgroundColor(),
                board.getImageUrl(),
                board.getWorkspace().getTitle()
        );
    }
}
