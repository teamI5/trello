package com.sparta.trellocopy.domain.board.service;

import com.sparta.trellocopy.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    // 보드 만들기

    //자신이 속해있는 워크스페이스의 보드 단건 조회

    // 보드 수정(이름, 배경색, 이미지)

    // 보드 삭제
}
