package com.sparta.trellocopy.domain.card.service;

import com.sparta.trellocopy.domain.card.dto.req.AddCardUserRequest;
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
import com.sparta.trellocopy.domain.list.exception.ListNotFoundException;
import com.sparta.trellocopy.domain.list.exception.ListNotInWorkSpaceException;
import com.sparta.trellocopy.domain.list.repository.ListRepository;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.CardUser;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.entity.WorkspaceRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.user.exception.CardUserNotFoundException;
import com.sparta.trellocopy.domain.user.exception.WorkspaceUserNotFoundException;
import com.sparta.trellocopy.domain.user.repository.CardUserRepository;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor    // 의존성 주입을 위해 final로 선언된 필드의 생성자를 자동으로 만들어준다.
@Transactional(readOnly = true)     // 읽기 전용 트랜잭션(DB의 부하를 줄여준다.)
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
            throw new CardForbiddenException(); // (카드에 대한 권한이 없을때)라는 특정 상황을 처리하기위해, 일관성
        }

        // 리스트 존재 확인
        Lists list = listRepository.findById(cardSaveRequest.getListId())
            .orElseThrow(() -> new ListNotFoundException());

        // 사용자로부터 받은 리스트 아이디가 해당 워크스페이스와 연결된 리스트의 아이디인지
        if (!list.getBoard().getWorkspace().getId().equals(cardSaveRequest.getWorkSpaceId())) {
            throw new ListNotInWorkSpaceException();
        }

        // 생성자로 객체를 생성
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
     * 권한있는 사용자가 카드를 수정하는 로직
     *
     * @param cardId 카드의 ID
     * @param request 카드 수정 정보를 포함한 요청 객체(제목, 내용, 마감일, 파일URL)
     * @param authUser 인증된 사용자(카드를 생성하는 사용자)
     * @return CardSimpleResponse - 생성된 카드에 대한 메세지, 사용자 이메일, 상태 코드를 포함한 응답 객체
     * @throws NotFoundException 사용자가 존재하지않는 경우 발생
     * @throws CardForbiddenException 사용자가 해당 워크스페이스에 카드를 생성할 권한이 없는 경우 발생
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

        // 새 값으로 업데이트
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
    public CardSimpleResponse updateCardWithLock(Long cardId, CardSaveRequest request, AuthUser authUser) {
        // 유저 존재 확인
        User user = userRepository.findById(authUser.getId())
            .orElseThrow(() -> new NotFoundException("User not found"));

        String role = getWorkSpaceUserRole(request.getWorkSpaceId(), authUser);
        if(role.equals(WorkspaceRole.READ_ONLY)){
            throw new CardForbiddenException();
        }

        Card card = cardRepository.findByIdOrElseThrowPessimistic(cardId);

        card.update(
            request.getTitle(),
            request.getContents(),
            request.getDeadline(),
            request.getFile_url()
        );
        // 영속성 컨텍스트에서 이미 존재하는 객체는 변경사항을 업데이트해주기 때문에, 별도로 저장할 필요가 없다.

        return new CardSimpleResponse(
            "Card updated successfully",
            user.getEmail(), //card.getUser.getEmail,
            200
        );
    }

    /**
     * 권한있는 사용자가 카드를 삭제하는 로직
     *
     * @param cardId 카드의 ID
     * @param request 사용자의 권한을 확인하기 위한 정보를 포함한 요청 객체(workSpaceId)
     * @param authUser 인증된 사용자(카드를 생성하는 사용자)
     * @return CardSimpleResponse - 생성된 카드에 대한 메세지, 사용자 이메일, 상태 코드를 포함한 응답 객체
     * @throws NotFoundException 사용자가 존재하지않는 경우 발생
     * @throws CardForbiddenException 사용자가 해당 워크스페이스에 카드를 생성할 권한이 없는 경우 발생
     */
    @Transactional
    public CardSimpleResponse deleteCard(Long cardId, CardSimpleRequest request, AuthUser authUser){
        // 유저의 이메일을 출력하기 위함
        User user = userRepository.findById(authUser.getId())
            .orElseThrow(() -> new NotFoundException("User not found"));

        // 수정 권한 있는지 확인
        String role = getWorkSpaceUserRole(request.WorkSpaceId, authUser);
        if(role.equals(WorkspaceRole.READ_ONLY)){
            throw new CardForbiddenException();
        }

        // (OrElseThrow)캡슐화->재사용, 예외 일관성유지
        cardRepository.findByIdOrElseThrow(cardId);

        cardRepository.deleteById(cardId);

        return new CardSimpleResponse(
            "Card deleted successfully",
            user.getEmail(), //card.getUser.getEmail,
            200
        );
    }

    /**
     * 카드를 단건 조회하는 로직
     *
     * @param cardId 카드의 ID
     * @return CardDetailResponse - 특정 카드의 ID, 제목, 설명, 데드라인, 파일URL, 생성일, 수정일을 포함한 응답 객체
     */
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

    /**
     * 특정 워크스페이스에서 사용자의 권한을 조회하는 메서드
     *
     * @param workspaceId 해당 카드가 속해있는 워크스페이스 ID
     * @param authUser 현재 인증된 사용자
     * @return String - 사용자의 권한 이름
     * @throws IllegalArgumentException 해당 유저가 워크스페이스에 존재하지 않는 경우 발생
     */
    public String getWorkSpaceUserRole(Long workspaceId, AuthUser authUser){
        WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, authUser.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 워크스페이스에 존재하지 않습니다."));

        return workspaceUser.getRole().name();
    }

    /**
     * 특정 조건에 맞는 카드를 Page로 조회
     *
     * @param page 페이지 번호(1부터 시작)
     * @param size 페이지당 카드 수
     * @param request - 검색 조건을 포함한 요청 객체
     * @param authUser 현재 인증된 사용자 정보
     * @return Page<CardDetailResponse> - 검색된 카드의 상세 정보를 포함한 페이지 객체
     * @throws WorkspaceUserNotFoundException 사용자가 해당 워크스페이스에 존재하지 않는 경우 발생
     */
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

    /**
     * 이미 생성된 카드에 담당자를 추가하는 로직
     *
     * @param cardId 카드의 ID
     * @param addCardUserRequest 추가할 담당자의 정보를 포함한 요청 객체
     * @return CardSimpleResponse - 생성된 카드에 대한 메세지, 사용자 이메일, 상태 코드를 포함한 응답 객체
     * @throws NotFoundException 유저가 존재하지않을때 발생
     * @throws IllegalArgumentException 해당 워크스페이스에 유저의 정보가 존재하지 않을 때 발생
     * @throws IllegalArgumentException 중복 기입되었을때 발생
     */
    @Transactional
    public CardSimpleResponse addCardUser(Long cardId, AddCardUserRequest addCardUserRequest){
        // 카드 존재 확인
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        // 유저 존재 확인
        User user = userRepository.findByEmail(addCardUserRequest.getEmail())
            .orElseThrow(() -> new NotFoundException("User not found"));


        // 워크스페이스에 해당 유저 존재 확인
        WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(addCardUserRequest.getWorkSpaceId(), user.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 워크스페이스에 존재하지 않습니다."));

        // 중복 기입 안되게
        if(cardUserRepository.existsByCardAndUser(card, user)){
            throw new IllegalArgumentException("이미 해당 카드에 이 사용자가 추가되어 있습니다.");
        }

        // 담당자 추가
        CardUser cardUser = new CardUser(card, user);
        card.addCardUser(cardUser);

        cardUserRepository.save(cardUser);

        return new CardSimpleResponse(
            "0",
            user.getEmail(),
            200
        );
    }

    /**
     * 카드 사용자를 삭제하는 메서드
     *
     * @param cardId 삭제할 카드 사용자 ID
     * @param email 삭제할 카드 사용자 ID
     * @throws CardUserNotFoundException 카드 사용자가 존재하지 않는 경우 발생
     */
    @Transactional
    public void deleteCardUser(Long cardId, String email) {
        CardUser cardUser = cardUserRepository.findByCardIdAndUserEmail(cardId, email)
            .orElseThrow(() -> new CardUserNotFoundException());
        cardUserRepository.delete(cardUser);
    }

}
