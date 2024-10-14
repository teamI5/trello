package com.sparta.trellocopy.domain.list.service;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.board.repository.BoardRepository;
import com.sparta.trellocopy.domain.common.exception.InvalidRequestException;
import com.sparta.trellocopy.domain.list.dto.request.ListSaveRequest;
import com.sparta.trellocopy.domain.list.dto.response.ListSaveResponse;
import com.sparta.trellocopy.domain.list.entity.List;
import com.sparta.trellocopy.domain.list.repository.ListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListService {

    private final ListRepository listRepository;
    private final BoardRepository boardRepository;

    public ListSaveResponse saveLists(ListSaveRequest request, Long boardId) {

        Integer orderNumber=0;
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new InvalidRequestException("board를 찾을 수 없습니다."));


        orderNumber++;
        List newList = new List(
                request.getTitle(),
                board,
                orderNumber
        );
        List saveList = listRepository.save(newList);

        return new ListSaveResponse(
                saveList.getTitle()
        );

    }


}
