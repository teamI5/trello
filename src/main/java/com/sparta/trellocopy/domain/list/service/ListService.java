package com.sparta.trellocopy.domain.list.service;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.board.repository.BoardRepository;
import com.sparta.trellocopy.domain.common.exception.NotFoundException;
import com.sparta.trellocopy.domain.list.dto.request.ListSaveRequest;
import com.sparta.trellocopy.domain.list.dto.request.ListUpdateRequest;
import com.sparta.trellocopy.domain.list.dto.response.ListSaveResponse;
import com.sparta.trellocopy.domain.list.dto.response.ListUpdateResponse;
import com.sparta.trellocopy.domain.list.entity.Lists;
import com.sparta.trellocopy.domain.list.repository.ListRepository;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Transactional
    public ListSaveResponse saveLists(AuthUser authUser, ListSaveRequest request, Long boardId) {

        // 읽기 전용 역할을 가진 멤버가 리스트를 생성/수정하려는 경우
        //읽기 전용 역할을 가진 멤버가 리스트를 삭제하려는 경우
        long orderNumbercount = listRepository.count();
        long orderNumber = orderNumbercount + 1;
        Board board = boardRepository.findById(boardId)
                .orElseThrow(NotFoundException::new);

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
    public List<ListUpdateResponse> updateOrderNumbers(AuthUser authUser, ListUpdateRequest request) {

        Lists changelist = listRepository.findByOrderNumber(request.getOrderNumber())
                .orElseThrow(NotFoundException::new);
        Lists secondchange = listRepository.findByOrderNumber(request.getNewOrderNumber())
                        .orElseThrow(NotFoundException::new);
        Long templistorderNumber = changelist.getOrderNumber();

        changelist.update(secondchange.getOrderNumber());
        secondchange.update(templistorderNumber);

        List<Lists> changedList = listRepository.orderNumberAsc();

        return changedList.stream().map(list -> new ListUpdateResponse(
                        list.getTitle(),
                        list.getOrderNumber()
                ))
                .collect(Collectors.toList());


    }
}
