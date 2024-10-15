package com.sparta.trellocopy.domain.board.controller;

import com.sparta.trellocopy.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    // 보드 만들기
    @PostMapping
    public ResponseEntity

    //자신이 속해있는 워크스페이스의 보드 단건 조회

    // 보드 수정(이름, 배경색, 이미지)

    // 보드 삭제
}
