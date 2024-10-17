package com.sparta.trellocopy.domain.workspace.service;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.entity.UserRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import com.sparta.trellocopy.domain.workspace.dto.WorkspaceResponse;
import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import com.sparta.trellocopy.domain.workspace.exception.WorkspaceBadRequestException;
import com.sparta.trellocopy.domain.workspace.repository.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(MockitoExtension.class)
class WorkspaceServiceTest {

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private WorkspaceUserRepository workspaceUserRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WorkspaceService workspaceService;
    User user = User.builder()
            .email("e@ma.il")
            .build();

    WorkspaceUser workspaceUser = WorkspaceUser.builder()
            .user(user)
            .role(WorkspaceRole.WORKSPACE)
            .build();
    List<WorkspaceUser> workspaceUserList = List.of(workspaceUser);

    Workspace workspace = Workspace.builder()
            .users(workspaceUserList)
            .build();
    Long workspaceId = 1L;
    String email = "e@ma.il";
    AuthUser authUser = new AuthUser(1L, "2e@ma.il", UserRole.valueOf("ROLE_ADMIN"));


    @BeforeEach
    void setUp(){
        //setField(user, "email","e@ma.il");
        //setField(workspaceUser, "user", user);
        //setField(workspaceUser, "role", WorkspaceRole.WORKSPACE);
        //setField(workspace, "users", workspaceUser);
    }

    @Test
    void addUser() throws InterruptedException{
        //g
        given(workspaceRepository.findById(workspaceId)).willReturn(Optional.of(workspace));
        given(workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, authUser.getId())).willReturn(Optional.of(workspaceUser));

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(1); // 첫 번째 스레드 일시 정지
        //w
        //WorkspaceResponse response = workspaceService.addUserAtWorkSpace(workspaceId, email, authUser);
        // 첫 번째 스레드
        WorkspaceBadRequestException exception = assertThrows(WorkspaceBadRequestException.class, ()-> {
            Runnable firstThreadTask = () -> {
                try {
                    workspaceService.addUserAtWorkSpace(workspaceId, email, authUser);
                    // 여기서 일시 정지
                    latch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (RuntimeException e) {
                    // 예외 처리
                }
            };

            // 두 번째 스레드
            Runnable secondThreadTask = () -> {
                try {
                    // 잠시 대기하여 첫 번째 스레드가 시작할 수 있도록 함
                    Thread.sleep(100);
                    workspaceService.addUserAtWorkSpace(workspaceId, email, authUser);
                } catch (InterruptedException e) {
                    // 예외 처리
                }
            };

            // 두 스레드 실행
            executorService.submit(firstThreadTask);
            executorService.submit(secondThreadTask);

            // 첫 번째 스레드 일시 정지 해제
            Thread.sleep(100); // 두 번째 스레드가 실행될 수 있도록 잠시 대기
            latch.countDown(); // 첫 번째 스레드 재개

            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        });

        //t
        assertEquals("이미 가입된 사용자입니다.", exception.getMessage());
    }

}



