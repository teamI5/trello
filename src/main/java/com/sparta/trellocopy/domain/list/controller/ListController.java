package com.sparta.trellocopy.domain.list.controller;

import com.sparta.trellocopy.domain.list.dto.request.ListSaveRequest;
import com.sparta.trellocopy.domain.list.dto.request.ListUpdateRequest;
import com.sparta.trellocopy.domain.list.dto.response.ListSaveResponse;
import com.sparta.trellocopy.domain.list.dto.response.ListUpdateResponse;
import com.sparta.trellocopy.domain.list.service.ListService;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lists")
@RequiredArgsConstructor
public class ListController {
    private final ListService listService;

    /**
     * List 저장 로직
     * request : 제목 , 순서 , AuthUser
     * response : 제목
     */

    @PostMapping("/boards/{boardId}")
    public ResponseEntity<ListSaveResponse> saveLists(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody ListSaveRequest request,
            @PathVariable Long boardId) {

        return ResponseEntity.ok(listService.saveLists(authUser, request, boardId));
    }

    /**
     * List 순서 변경 로직
     * request  : 바꿀 순서의 리스트 번호들
     * response : 제목 , 순서,AuthUser
     */
    @PutMapping("/boards/{boardId}")
    public ResponseEntity <List<ListUpdateResponse>> updateOrderNumbers(

            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody ListUpdateRequest request,
            @PathVariable Long boardId
    ) {
        return ResponseEntity.ok(listService.updateOrderNumbers(authUser,request,boardId));
    }
}
