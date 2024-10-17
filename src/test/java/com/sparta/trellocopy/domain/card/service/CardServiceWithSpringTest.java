package com.sparta.trellocopy.domain.card.service;

import com.sparta.trellocopy.domain.card.dto.req.CardSaveRequest;
import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.repository.CardRepository;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CardServiceWithSpringTest {

    private CardService cardService;
    private CardRepository cardRepository;

    @Test
    public void 카드수정_락없이_테스트() throws InterruptedException {
        // Given
        Long cardId = 1L;
        AuthUser authUser = new AuthUser(1L, "user@user.com", UserRole.ROLE_USER);
        CardSaveRequest req = new CardSaveRequest(
            3L,
            1L,
            "update Title",
            "update contents",
            LocalDateTime.now(),
            ""
        );

        // When
        int threadCount = 10;
        int repeatCount = 100;
        // 스레드 풀 설정(10개 고정)
        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        // CountDownLatch 초기화, 100번 실행된 작업 대기
        CountDownLatch latch = new CountDownLatch(repeatCount);

        for(int i = 0; i < repeatCount; i++){
            service.submit(() -> {
                try {
                    // 카드 수정 로직 호출
                    cardService.updateCard(cardId, req, authUser);
                } finally {
                    latch.countDown();
                }
            });
        }

        // countDownLatch가 0이 될때까지 대기(모든 작업 완료 대기)
        latch.await();

        service.shutdown();

        // Then
        // Card의 최종 상태를 확인하는 로직 (동시성 문제로 인한 충돌 여부 확인)
        Card updatedCard = cardRepository.findById(cardId).orElseThrow();
        assertEquals("Updated Title", updatedCard.getTitle());

    }

    @Test
    @Transactional
    public void 카드수정_비관락_테스트() throws InterruptedException {
        // given
        Long cardId = 1L;
        AuthUser authUser = new AuthUser(1L, "user@user.com", UserRole.ROLE_USER);

        // when
        int threadCount = 10;   // 동시에 실행할 스레드수
        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(100);

        for(int i = 0; i < 100; i++){
            final int threadNum = i;
            service.execute(() -> {
                try {
                    // 카드 업데이트 요청 생성
                    CardSaveRequest req = new CardSaveRequest(
                        3L,
                        1L,
                        "title" + threadNum,
                        "contents " + threadNum,
                        null,
                        ""
                    );
                    // 업데이트 로직 호출
                    cardService.updateCard(cardId, req, authUser);
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        // 모든 스레드가 끝날때까지 대기
        latch.await();
        service.shutdown();

        // then
        // 예상대로 업데이트 되었는지 검증
        Card card = cardRepository.findByIdOrElseThrow(cardId);
        assertNotNull(card);
        System.out.println("Final title: " + card.getTitle());
        System.out.println("Final content : " + card.getContents());

    }

}