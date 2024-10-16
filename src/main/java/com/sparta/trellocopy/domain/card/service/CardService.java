package com.sparta.trellocopy.domain.card.service;

import com.sparta.trellocopy.domain.card.dto.req.CardSaveRequest;
import com.sparta.trellocopy.domain.card.dto.req.CardSearchRequest;
import com.sparta.trellocopy.domain.card.dto.req.CardSimpleRequest;
import com.sparta.trellocopy.domain.card.dto.res.CardDetailResponse;
import com.sparta.trellocopy.domain.card.dto.res.CardSimpleResponse;
import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.exception.CardForbiddenException;
import com.sparta.trellocopy.domain.card.repository.CardRepository;
import com.sparta.trellocopy.domain.common.exception.NotFoundException;
import com.sparta.trellocopy.domain.list.entity.Lists;
import com.sparta.trellocopy.domain.list.repository.ListRepository;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.CardUser;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.entity.WorkspaceRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.user.repository.CardUserRepository;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {
    private final CardRepository cardRepository;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final UserRepository userRepository;
    private final CardUserRepository cardUserRepository;
    private final ListRepository listRepository;

    /**
     * 로그인한 사용자가 카드를 생성하는 로직
     *
     * @param cardSaveRequest 카드 세부 정보를 포함한 요청 객체(제목, 내용, 마감일, 파일URL)
     * @param authUser 인증된 사용자(카드를 생성하는 사용자)
     * @return CardSimpleResponse - 생성된 카드에 대한 메세지, 사용자 이메일, 상태 코드를 포함한 응답 객체
     * @throws NotFoundException 사용자가 존재하지않는 경우 발생
     * @throws CardForbiddenException 사용자가 해당 워크스페이스에 카드를 생성할 권한이 없는 경우 발생
     */
    @Transactional
    public CardSimpleResponse createCard(CardSaveRequest cardSaveRequest, AuthUser authUser) {
        // 유저 존재 확인
        User user = userRepository.findById(authUser.getId())
            .orElseThrow(() -> new NotFoundException("User not found"));

        // 권한 확인
        String role = getWorkSpaceUserRole(cardSaveRequest.getWorkSpaceId(), authUser);
        if(role.equals(WorkspaceRole.READ_ONLY)){
            throw new CardForbiddenException();
        }

        Lists list = listRepository.findById(cardSaveRequest.getListId())
            .orElseThrow(() -> new NotFoundException("List not found"));

        if (!list.getBoard().getWorkspace().getId().equals(cardSaveRequest.getWorkSpaceId())) {
            throw new IllegalArgumentException("The list does not belong to the specified workspace.");
        }

        Card card = new Card(
            cardSaveRequest.getTitle(),
            cardSaveRequest.getContents(),
            cardSaveRequest.getDeadline(),
            cardSaveRequest.getFile_url(),
            list
        );


        CardUser cardUser = new CardUser(card, user);
        card.addCardUser(cardUser);

        cardRepository.save(card);
        cardUserRepository.save(cardUser);

        return new CardSimpleResponse(
            "Card created successfully",
            user.getEmail(),
            200
        );
    }

    /**
     *
     * @param cardId
     * @param request
     * @param authUser
     * @return
     */
    @Transactional
    public CardSimpleResponse updateCard(Long cardId, CardSaveRequest request, AuthUser authUser) {
        // 유저 존재 확인
        User user = userRepository.findById(authUser.getId())
            .orElseThrow(() -> new NotFoundException("User not found"));

        String role = getWorkSpaceUserRole(request.getWorkSpaceId(), authUser);
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
            "Card updated successfully",
            user.getEmail(), //card.getUser.getEmail,
            200
        );
    }

    @Transactional
    public CardSimpleResponse deleteCard(Long cardId, CardSimpleRequest request, AuthUser authUser){
        // 유저 존재 확인
        User user = userRepository.findById(authUser.getId())
            .orElseThrow(() -> new NotFoundException("User not found"));

        String role = getWorkSpaceUserRole(request.WorkSpaceId, authUser);
        if(role.equals(WorkspaceRole.READ_ONLY)){
            throw new CardForbiddenException();
        }

        Card card = cardRepository.findByIdOrElseThrow(cardId);

        cardRepository.deleteById(cardId);

        return new CardSimpleResponse(
            "Card deleted successfully",
            user.getEmail(), //card.getUser.getEmail,
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

    public String getWorkSpaceUserRole(Long workspaceId, AuthUser authUser){
        WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, authUser.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 워크스페이스에 존재하지 않습니다."));

        return workspaceUser.getRole().name();
    }

//    public Page<CardDetailResponse> searchCards(int page, int size, CardSearchRequest request, AuthUser authUser){
//        Pageable pageable = PageRequest.of(page - 1, size);
//        Page<Card> cards = cardRepository.searchCards(pageable, request.getWorkSpaceId(), request.getTitle(), request.getContents(), request.getCardUser(), request.getDeadline());
//
//        return cards.map(card -> new CardDetailResponse(
//            card.getId(),
//            card.getTitle(),
//            card.getContents(),
//            card.getDeadline(),
//            card.getFile_url(),
//            card.getCreatedAt(),
//            card.getModifiedAt()
//            )
//        );
//    }

    /**
     * Card의 담당자를 추가하는 로직
     */
    //public CardSimpleResponse update

}
