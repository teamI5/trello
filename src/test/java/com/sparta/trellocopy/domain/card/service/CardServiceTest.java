package com.sparta.trellocopy.domain.card.service;
import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.card.dto.req.CardSaveRequest;
import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.repository.CardRepository;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.entity.UserRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;


public class CardServiceTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WorkspaceUserRepository workspaceUserRepository;



    @BeforeEach
    public void setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this);

        // Mock 설정: 빌더 패턴을 사용하여 User 객체 생성
        User user = User.builder()
            .email("user@user.com")
            .password("User1234!")
            .role(UserRole.ROLE_USER)
            .build();

        // Mock 설정: 카드 생성 시 빌더 패턴을 사용해 카드 객체 생성
        Card card = new Card("title1", "content1", LocalDateTime.now(), "", null);
// WorkspaceUser와 Board 객체 생성
        List<Board> boards = new ArrayList<>(); // 빈 리스트로 시작하거나 필요한 Board 객체 추가
        List<WorkspaceUser> users = new ArrayList<>();
        Workspace workspace = new Workspace("workspace","워크", boards, users);
        Board board = new Board("Board", "", "", workspace);
        WorkspaceUser workspaceUser = WorkspaceUser.builder()
                .user(user)
                .workspace(workspace)
                .role(WorkspaceRole.WORKSPACE)
                .build();
        boards.add(board);
        users.add(workspaceUser);





        // Mock 설정: 사용자가 존재하는 경우를 시뮬레이션
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // Mock 설정: 카드가 존재하는 경우를 시뮬레이션
        when(cardRepository.findByIdOrElseThrow(anyLong())).thenReturn(card);

        when(workspaceUserRepository.findByWorkspaceIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(workspaceUser));

    }

    @Test
    public void updateCardWithoutLock_MultiThreadTest() throws InterruptedException {
        // Given
        Long cardId = 1L;
        AuthUser authUser = new AuthUser(1L, "user@user.com", UserRole.ROLE_USER);


        int threadCount = 10; // 동시에 실행할 스레드 수
        int repeatCount = 100; // 실행할 작업 총 횟수
        AtomicInteger attemptCounter = new AtomicInteger(0); // 시도 횟수를 세기 위한 카운터

        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(repeatCount);

        // 테스트 시작 시간 기록
        long startTime = System.currentTimeMillis();

        // When
        for (int i = 0; i < repeatCount; i++) {
            final int threadNum = i;
            service.execute(() -> {
                try {
                    CardSaveRequest req = new CardSaveRequest(
                        3L,
                        1L,
                        "title" + threadNum,
                        "contents " + threadNum,
                        null,
                        ""
                    );
                    // 카드 수정 로직 호출
                    cardService.updateCard(cardId, req, authUser); // 락 없이 카드 수정
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 시도 카운트 증가
                    attemptCounter.incrementAndGet();
                    latch.countDown(); // 작업 완료 시 latch 카운트 감소
                }
            });
        }

        // CountDownLatch가 0이 될 때까지 대기
        latch.await();

        // 스레드 풀 종료
        service.shutdown();

        // 테스트 종료 시간 기록
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Card updated without lock in " + (duration) + " ms"); // 시간 출력
        System.out.println("Total update attempts: " + attemptCounter.get()); // 총 시도 횟수 출력

        // Then
        // 카드가 업데이트된 횟수를 확인하기 위한 mock 확인
        verify(cardRepository, times(repeatCount)).findByIdOrElseThrow(anyLong()); // findById가 정확히 100번 호출되었는지 확인

        Card updatedCard = cardRepository.findByIdOrElseThrow(cardId);
        System.out.println("Final card title (no lock): " + updatedCard.getTitle());
        System.out.println("Final card content (no lock): " + updatedCard.getContents());
    }

    @Test
    @DisplayName("비관적 락 테스트")
    public void updateCardWithPessimisticLock_MultiThreadTest() throws InterruptedException {
        // Given
        Long cardId = 1L;
        AuthUser authUser = new AuthUser(1L, "user@user.com", UserRole.ROLE_USER);

        // 같은 ID로 존재하는 카드를 생성해야 합니다.
        Card existingCard = new Card("Initial Title", "Initial Content", LocalDateTime.now(), "", null);
        when(cardRepository.findByIdOrElseThrowPessimistic(anyLong())).thenReturn(existingCard);



        int threadCount = 10; // 동시에 실행할 스레드 수
        int repeatCount = 100; // 실행할 작업 총 횟수
        AtomicInteger attemptCounter = new AtomicInteger(0); // 시도 횟수를 세기 위한 카운터

        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(repeatCount);

        // 테스트 시작 시간 기록
        long startTime = System.currentTimeMillis();

        // When
        for (int i = 0; i < repeatCount; i++) {
            final int threadNum = i;
            service.execute(() -> {
                try {
                    CardSaveRequest req = new CardSaveRequest(
                        3L,
                        1L,
                        "title" + threadNum,
                        "contents " + threadNum,
                        null,
                        ""
                    );
                    // 카드 수정 로직 호출
                    cardService.updateCardWithLock(cardId, req, authUser); // 락 없이 카드 수정
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 시도 카운트 증가
                    attemptCounter.incrementAndGet();
                    latch.countDown(); // 작업 완료 시 latch 카운트 감소
                }
            });
        }

        // CountDownLatch가 0이 될 때까지 대기
        latch.await();

        // 스레드 풀 종료
        service.shutdown();

        // 테스트 종료 시간 기록
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Card updated with lock in " + (duration) + " ms"); // 시간 출력
        System.out.println("Total update attempts: " + attemptCounter.get()); // 총 시도 횟수 출력

        // Then
        // 카드가 업데이트된 횟수를 확인하기 위한 mock 확인
        verify(cardRepository, times(repeatCount)).findByIdOrElseThrowPessimistic(cardId); // findById가 정확히 100번 호출되었는지 확인

        Card updatedCard = cardRepository.findByIdOrElseThrow(cardId);
        System.out.println("Final card title (no lock): " + updatedCard.getTitle());
        System.out.println("Final card content (no lock): " + updatedCard.getContents());
    }

}
