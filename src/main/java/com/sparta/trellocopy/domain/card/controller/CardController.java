package com.sparta.trellocopy.domain.card.controller;

import com.sparta.trellocopy.domain.card.dto.req.CardSaveRequest;
import com.sparta.trellocopy.domain.card.dto.res.CardSimpleResponse;
import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/card")
    public ResponseEntity<CardSimpleResponse> createdCard(@RequestBody CardSaveRequest cardSaveRequest){
        return ResponseEntity.ok(cardService.createdCard(cardSaveRequest));
    }

    @PutMapping("/card/{cardId}")
    public ResponseEntity<CardSimpleResponse> updatedCard(@RequestParam Long cardId, @RequestBody CardSaveRequest request){
        return ResponseEntity.ok(cardService.updatedCard(cardId, request));
    }

    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<CardSimpleResponse> deletedCard(@RequestParam Long cardId){
        return ResponseEntity.ok(cardService.deletedCard(cardId));
    }
}
