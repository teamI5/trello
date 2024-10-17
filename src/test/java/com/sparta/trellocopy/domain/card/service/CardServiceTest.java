package com.sparta.trellocopy.domain.card.service;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.card.dto.req.CardSaveRequest;
import com.sparta.trellocopy.domain.card.dto.req.CardSimpleRequest;
import com.sparta.trellocopy.domain.card.dto.res.CardSimpleResponse;
import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.repository.CardRepository;
import com.sparta.trellocopy.domain.list.entity.Lists;
import com.sparta.trellocopy.domain.list.repository.ListRepository;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.entity.UserRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.user.repository.CardUserRepository;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import com.sparta.trellocopy.domain.workspace.repository.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

public class CardServiceTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private ListRepository listRepository;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private WorkspaceUserRepository workspaceUserRepository;
    @Mock
    private CardUserRepository cardUserRepository; // 이 필드도 @Mock으로 설정해야 합니다.


    private AuthUser authUser;
    private CardSaveRequest cardSaveRequest;
    private Card card;
    private User user;
    private Lists lists;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        authUser = new AuthUser(1L, "user@user.com", UserRole.ROLE_USER); // 기본 사용자 설정

        user = User.builder()
            .email("user@user.com")
            .password("User1234!")
            .role(UserRole.ROLE_USER)
            .build();

        List<Board> boards = new ArrayList<>();
        List<WorkspaceUser> workspaceUsers = new ArrayList<>();

        Workspace workspace = Workspace.builder()
            .title("workspace1")
            .description("워크스페이스1")
            .boards(boards)
            .users(workspaceUsers)
            .build();

        setField(workspace, "id", 1L); // Workspace의 id를 1L로 설정


        WorkspaceUser workspaceUser = WorkspaceUser.builder()
            .user(user)
            .workspace(workspace)
            .role(WorkspaceRole.WORKSPACE)
            .build();

        Board board = new Board("Board", "", "", workspace);

        lists = new Lists("list", board, 1L);

        card = new Card("title1", "content1", LocalDateTime.now(), "", null);
        setField(card, "id", 1L);


        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(workspaceRepository.findById(anyLong())).thenReturn(Optional.of(workspace));
        when(workspaceUserRepository.findByWorkspaceIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(workspaceUser));
        when(listRepository.findById(anyLong())).thenReturn(Optional.of(lists));
        when(cardRepository.findById(anyLong())).thenReturn(Optional.of(card));
    }

    @Test
    public void 카드생성_성공() {
        // Given
        cardSaveRequest = new CardSaveRequest(1L, 1L, "New Card", "Card content", LocalDateTime.now(), "");

        // When
        CardSimpleResponse response = cardService.createCard(cardSaveRequest, authUser);

        // Then
        assertNotNull(response);
        assertEquals("Card created successfully", response.getMessage());
        verify(cardRepository, times(1)).save(any()); // 카드 저장 메소드가 호출되었는지 확인
    }

    @Test
    public void updateCard_success() {
        // Given
        Long cardId = 1L;
        Card existingCard = new Card("Existing Card", "Existing contents", LocalDateTime.now(), "", lists);
        CardSaveRequest request = new CardSaveRequest(1L, 1L, "Updated Card", "Updated contents", null, null);

        when(userRepository.findById(authUser.getId())).thenReturn(Optional.of(user));
        when(cardRepository.findByIdOrElseThrow(cardId)).thenReturn(existingCard);

        // When
        CardSimpleResponse response = cardService.updateCard(cardId, request, authUser);

        // Then
        verify(cardRepository).save(existingCard); // save 호출 확인
        assertEquals("Card updated successfully", response.getMessage());
    }

    @Test
    public void deleteCard_success() throws Exception {
        // Given
        Long cardId = 1L; // ID를 명시적으로 설정
        Card existingCard = new Card("Card to Delete", "Contents", null, null, lists);
        setField(existingCard, "id", cardId); // 카드의 ID를 설정

        when(userRepository.findById(authUser.getId())).thenReturn(Optional.of(user));
        when(cardRepository.findByIdOrElseThrow(cardId)).thenReturn(existingCard);

        // When
        CardSimpleResponse response = cardService.deleteCard(cardId, new CardSimpleRequest(1L, 1L), authUser);

        // Then
        verify(cardRepository).deleteById(cardId); // cardId를 사용하여 검증
        assertEquals("Card deleted successfully", response.getMessage());
    }




    private void setField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true); // 접근 제한자 무시
        field.set(object, value); // 필드 값 설정
    }

}
