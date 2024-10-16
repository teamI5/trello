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
    private final List<String> boards;
    private final List<String> userEmail;

    public WorkspaceResponse(Long id, String title, String description, List<String> boards, List<String> userEmail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.boards = boards;
        this.userEmail = userEmail;
    }

    public static WorkspaceResponse fromWorkspace(Workspace workspace) {
        return new WorkspaceResponse(
                workspace.getId(),
                workspace.getTitle(),
                workspace.getDescription(),
                workspace.getBoards().stream()
                                .map(Board::getTitle).toList(),
                workspace.getUsers().stream()
                        .map(workspaceUser -> workspaceUser.getUser().getEmail())
                        .collect(Collectors.toList())
        );
    }
}
