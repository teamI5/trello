package com.sparta.trellocopy.domain.card.service;

import com.sparta.trellocopy.domain.card.dto.req.CardSaveRequest;
import com.sparta.trellocopy.domain.card.dto.res.CardSimpleResponse;
import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    public CardSimpleResponse createdCard(CardSaveRequest cardSaveRequest) {
        // user.role(view만 아니면 됨)
        // 로그인 상태인지
        //User user = User.fromAuthUser(authUser);

        Card card = new Card(
            cardSaveRequest.getTitle(),
            cardSaveRequest.getContents(),
            cardSaveRequest.getDeadline(),
            cardSaveRequest.getFile_url()
        );

        Card createdCard = cardRepository.save(card);

        return new CardSimpleResponse(
            "Card created sucessfully",
            "", //createdCard.getUser.getEmail,
            200
        );
    }
}
