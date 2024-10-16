package com.sparta.trellocopy.domain.list.dto.request;

import lombok.Getter;

@Getter
public class ListUpdateRequest {
    private Long listId;
    private Integer newPosition;

}
