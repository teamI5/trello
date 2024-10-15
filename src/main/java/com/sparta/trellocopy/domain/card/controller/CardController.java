package com.sparta.trellocopy.domain.card.controller;

import com.sparta.trellocopy.domain.card.dto.req.CardSaveRequest;
import com.sparta.trellocopy.domain.card.dto.req.CardSimpleRequest;
import com.sparta.trellocopy.domain.card.dto.res.CardDetailResponse;
import com.sparta.trellocopy.domain.card.dto.res.CardSimpleResponse;
import com.sparta.trellocopy.domain.card.service.CardService;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/card")
    public ResponseEntity<CardSimpleResponse> createdCard(@RequestBody CardSaveRequest cardSaveRequest, AuthUser authUser){
        return ResponseEntity.ok(cardService.createdCard(cardSaveRequest, authUser));
    }

    @PutMapping("/card/{cardId}")
    public ResponseEntity<CardSimpleResponse> updatedCard(@PathVariable Long cardId, @RequestBody CardSaveRequest request, AuthUser authUser){
        return ResponseEntity.ok(cardService.updatedCard(cardId, request, authUser));
    }

    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<CardSimpleResponse> deletedCard(@PathVariable Long cardId, @RequestBody CardSimpleRequest request, AuthUser authUser){
        return ResponseEntity.ok(cardService.deletedCard(cardId, request, authUser));
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<CardDetailResponse> getCard(@PathVariable Long cardId){
        return ResponseEntity.ok(cardService.getCard(cardId));
    }
}
