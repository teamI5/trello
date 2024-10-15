package com.sparta.trellocopy.domain.list.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ListUpdateResponse {
    private final String title;
    private final Long orderNumber;
}
