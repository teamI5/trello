package com.sparta.trellocopy.domain.card.controller;

import com.sparta.trellocopy.domain.card.dto.req.AddCardUserRequest;
import com.sparta.trellocopy.domain.card.dto.req.CardSaveRequest;
import com.sparta.trellocopy.domain.card.dto.req.CardSearchRequest;
import com.sparta.trellocopy.domain.card.dto.req.CardSimpleRequest;
import com.sparta.trellocopy.domain.card.dto.res.CardDetailResponse;
import com.sparta.trellocopy.domain.card.dto.res.CardSimpleResponse;
import com.sparta.trellocopy.domain.card.service.CardService;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/card")
    public ResponseEntity<CardSimpleResponse> createdCard(@RequestBody CardSaveRequest cardSaveRequest, @AuthenticationPrincipal AuthUser authUser){
        return ResponseEntity.ok(cardService.createCard(cardSaveRequest, authUser));
    }

    @PutMapping("/card/{cardId}")
    public ResponseEntity<CardSimpleResponse> updatedCard(@PathVariable Long cardId, @RequestBody CardSaveRequest request, @AuthenticationPrincipal  AuthUser authUser){
        return ResponseEntity.ok(cardService.updateCard(cardId, request, authUser));
    }

    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<CardSimpleResponse> deletedCard(@PathVariable Long cardId, @RequestBody CardSimpleRequest request, @AuthenticationPrincipal  AuthUser authUser){
        return ResponseEntity.ok(cardService.deleteCard(cardId, request, authUser));
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<CardDetailResponse> getCard(@PathVariable Long cardId){
        return ResponseEntity.ok(cardService.getCard(cardId));
    }

    @PostMapping("/card/{cardId}/add/cardUser")
    public ResponseEntity<CardSimpleResponse> addCardUser(@PathVariable Long cardId, @RequestBody AddCardUserRequest addCardUserRequest){
        return ResponseEntity.ok(cardService.addCardUser(cardId, addCardUserRequest));
    }

    @GetMapping("/card")
    public Page<CardDetailResponse> searchCards(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestBody CardSearchRequest request,
        @AuthenticationPrincipal AuthUser authUser){
        return cardService.searchCards(page, size, request, authUser);
    }

    @DeleteMapping("/card/{cardId}/del/cardUser")
    public void deleteCardUser(Long cardId, String email){
        cardService.deleteCardUser(cardId, email);
    }
}
