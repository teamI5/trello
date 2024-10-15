package com.sparta.trellocopy.domain.board.controller;

import com.sparta.trellocopy.domain.board.dto.BoardRequest;
import com.sparta.trellocopy.domain.board.dto.BoardResponse;
import com.sparta.trellocopy.domain.board.service.BoardService;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    // 보드 만들기
    @PostMapping("/{workspaceId}")
    public ResponseEntity<BoardResponse>saveBoard(
            @RequestBody BoardRequest boardRequest,
            @PathVariable Long workspaceId,
            @AuthenticationPrincipal AuthUser authUser
            ) {
        return ResponseEntity.ok(boardService.saveBoard(boardRequest, workspaceId, authUser));
    }

    //자신이 속해있는 워크스페이스의 보드 단건 조회

    // 보드 수정(이름, 배경색, 이미지)

    // 보드 삭제
}
