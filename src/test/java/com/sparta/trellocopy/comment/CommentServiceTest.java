package com.sparta.trellocopy.comment;

import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.repository.CardRepository;
import com.sparta.trellocopy.domain.comment.Service.CommentService;
import com.sparta.trellocopy.domain.comment.repository.CommentRepository;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.entity.UserRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WorkspaceUserRepository workspaceUserRepository;

    @InjectMocks
    private CommentService commentService;

    private Workspace mockWorkspace;
    private Card mockCard;
    private User mockUser;
    private WorkspaceUser mockWorkspaceUser;

    @BeforeEach
    void setUp() {

//        mockUser = User.builder()
//                .id(1L)
//                .email("test@test.com")
//                .password("password")
//                .role(UserRole.USER).build();
//        mockWorkspace = Workspace.builder()
//                .id(1L)
//                .name("Test Workspace").build();
//        mockWorkspaceUser = WorkspaceUser.builder()
//                .user(testUser)
//                .workspace(testWorkspace)
//                .role(WorkspaceRole.MEMBER).build();
//        mockCard = new Card(
//
//        )

    }


}
