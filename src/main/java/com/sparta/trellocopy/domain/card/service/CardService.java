package com.sparta.trellocopy.domain.card.service;

import com.sparta.trellocopy.domain.card.dto.req.CardSaveRequest;
import com.sparta.trellocopy.domain.card.dto.req.CardSimpleRequest;
import com.sparta.trellocopy.domain.card.dto.res.CardDetailResponse;
import com.sparta.trellocopy.domain.card.dto.res.CardSimpleResponse;
import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.exception.CardForbiddenException;
import com.sparta.trellocopy.domain.card.repository.CardRepository;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.WorkspaceRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final WorkspaceUserRepository workspaceUserRepository;

    public CardSimpleResponse createdCard(CardSaveRequest cardSaveRequest, AuthUser authUser) {
        String role = getWorkSpaceUser_Role(cardSaveRequest.WorkSpaceId, authUser);
        if(role.equals(WorkspaceRole.READ_ONLY)){
            throw new CardForbiddenException();
        }

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

    public CardSimpleResponse updatedCard(Long cardId, CardSaveRequest request, AuthUser authUser) {
        String role = getWorkSpaceUser_Role(request.WorkSpaceId, authUser);
        if(role.equals(WorkspaceRole.READ_ONLY)){
            throw new CardForbiddenException();
        }

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
    public CardSimpleResponse deletedCard(Long cardId, CardSimpleRequest request, AuthUser authUser){
        String role = getWorkSpaceUser_Role(request.WorkSpaceId, authUser);
        if(role.equals(WorkspaceRole.READ_ONLY)){
            throw new CardForbiddenException();
        }

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

    public String getWorkSpaceUser_Role(Long workspaceId, AuthUser authUser){
        WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, authUser.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 워크스페이스에 존재하지 않습니다."));

        return workspaceUser.getRole().name();
    }


}
