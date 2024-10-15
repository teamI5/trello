package com.sparta.trellocopy.domain.card.service;

import com.sparta.trellocopy.domain.card.dto.req.CardSaveRequest;
import com.sparta.trellocopy.domain.card.dto.res.CardDetailResponse;
import com.sparta.trellocopy.domain.card.dto.res.CardSimpleResponse;
import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.repository.CardRepository;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    public CardSimpleResponse createdCard(CardSaveRequest cardSaveRequest) {
        // user.role(view만 아니면 됨)
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

    public CardSimpleResponse updatedCard(Long cardId, CardSaveRequest request) {
        // user.role(view만 아니면 됨)

        Card card = cardRepository.findByIdOrElseThrow(cardId);

        card.update(
            request.getTitle(),
            request.getContents(),
            request.getDeadline(),
            request.getFile_url()
        );

        return new CardSimpleResponse(
            "Card updated sucessfully",
            "", //card.getUser.getEmail,
            200
        );
    }

    @Transactional
    public CardSimpleResponse deletedCard(Long cardId){
        // user.role(view만 아니면 됨)

        Card card = cardRepository.findByIdOrElseThrow(cardId);

        cardRepository.deleteById(cardId);

        return new CardSimpleResponse(
            "Card deleted sucessfully",
            "", //card.getUser.getEmail,
            200
        );
    }

    public CardDetailResponse getCard(Long cardId){
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        return new CardDetailResponse(
            card.getId(),
            card.getTitle(),
            card.getContents(),
            card.getDeadline(),
            card.getFile_url(),
            card.getCreatedAt(),
            card.getModifiedAt()
        );

    }
}
