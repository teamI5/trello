package com.sparta.trellocopy.domain.list.controller;

import com.sparta.trellocopy.domain.list.dto.request.ListSaveRequest;
import com.sparta.trellocopy.domain.list.dto.response.ListSaveResponse;
import com.sparta.trellocopy.domain.list.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/lists")
@RequiredArgsConstructor
public class ListController {
    private final ListService listService;

    /**
     * List 저장 로직
     * request : 제목 , 순서 , AuthUser
     * response : 제목
     */

    @PostMapping("/board/{boardId}")
    public ResponseEntity<ListSaveResponse> saveLists(
            @RequestBody ListSaveRequest request,
            @PathVariable Long boardId) {

        return ResponseEntity.ok(listService.saveLists(request,boardId));
    }
}
