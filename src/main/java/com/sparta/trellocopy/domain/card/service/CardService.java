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
import com.sparta.trellocopy.domain.user.exception.WorkspaceUserNotFoundException;
import com.sparta.trellocopy.domain.user.repository.CardUserRepository;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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


    public Page<CardDetailResponse> searchCards(int page, int size, CardSearchRequest request, AuthUser authUser){
        // 유저 존재 확인
        User user = userRepository.findByIdOrElseThrow(authUser.getId());

        // 워크스페이스 권한있는지
        Optional<WorkspaceUser> workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(request.getWorkSpaceId(), user.getId());
        if(!workspaceUser.isPresent()){
            throw new WorkspaceUserNotFoundException();
        }


        Pageable pageable = PageRequest.of(page - 1, size);

        LocalDateTime startOfDay = null;
        LocalDateTime endOfDay = null;
        if(request.getDeadline() != null){
            LocalDate searchDate = request.getDeadline();
            startOfDay = searchDate.atStartOfDay();
            endOfDay = searchDate.atTime(LocalTime.MAX);
        }



        Page<Card> cards = cardRepository.searchCards(pageable, request.getBoardId(), request.getTitle(),
            request.getContents(), request.getEmail(), startOfDay, endOfDay);


        return cards.map(card -> new CardDetailResponse(
            card.getId(),
            card.getTitle(),
            card.getContents(),
            card.getDeadline(),
            card.getFile_url(),
            card.getCreatedAt(),
            card.getModifiedAt()
            )
        );
    }

//    /**
//     * 이미 생성된 카드에 담당자를 추가하는 로직
//     *
//     * @param cardId
//     * @param addCardUserRequest
//     * @return
//     */
//    @Transactional
//    public CardSimpleResponse addCardUser(Long cardId, AddCardUserRequest addCardUserRequest){
//        // 카드 존재 확인
//        Card card = cardRepository.findByIdOrElseThrow(cardId);
//
//        // 유저 존재 확인
//        User user = userRepository.findByEmail(addCardUserRequest.getEmail())
//            .orElseThrow(() -> new NotFoundException("User not found"));
//
//
//        // 워크스페이스에 해당 유저 존재 확인
//        WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(addCardUserRequest.getWorkSpaceId(), user.getId())
//            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 워크스페이스에 존재하지 않습니다."));
//
//        // 중복 기입 안되게
//        Optional<CardUser> existingCardUser = cardUserRepository.findByCardIdAndUserId(cardId, user.getId());
//        if(existingCardUser.isPresent()){
//            log.info("User already exists as a card user: {}", user.getEmail());
//            throw new CardUserAlreadyExistsException();
//        }
//
//        // 담당자 추가
//        CardUser cardUser = new CardUser(card, user);
//        card.addCardUser(cardUser);
//
//        cardRepository.save(card);
//
//        return new CardSimpleResponse(
//            "0",
//            user.getEmail(),
//            200
//        );
//    }

}
