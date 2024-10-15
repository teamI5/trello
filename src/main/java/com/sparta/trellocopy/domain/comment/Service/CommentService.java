package com.sparta.trellocopy.domain.comment.Service;

import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.repository.CardRepository;
import com.sparta.trellocopy.domain.comment.Dto.CommentRequestDto;
import com.sparta.trellocopy.domain.comment.Dto.CommentResponseDto;
import com.sparta.trellocopy.domain.comment.Dto.CommentSaveRequestDto;
import com.sparta.trellocopy.domain.comment.Dto.CommentSaveResponseDto;
import com.sparta.trellocopy.domain.comment.entity.Comment;
import com.sparta.trellocopy.domain.comment.entity.Emoji;
import com.sparta.trellocopy.domain.comment.repository.CommentRepository;
import com.sparta.trellocopy.domain.common.exception.NotFoundException;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.entity.WorkspaceRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final WorkspaceUserRepository workspaceUserRepository;

    @Transactional
    public CommentSaveResponseDto createComment(long cardId,
                                                CommentSaveRequestDto commentSaveRequestDto,
                                                AuthUser authUser) {

        Card card = findCardById(cardId);
        User user = findUserById(authUser.getId());

        // 유저 역할 확인 필요 읽기 전용일 경우 에외처리
//        Long workspaceId = card.getWorkspace().getId();
//        checkUserRole(user, workspaceId);

        Comment comment = new Comment(
            commentSaveRequestDto.getContent(),
            user,
            card
        );

        Comment savedComment = commentRepository.save(comment);

        return new CommentSaveResponseDto(
            savedComment.getContent(),
            savedComment.getCreatedAt(),
            savedComment.getModifiedAt()
        );

    }

    public List<CommentResponseDto> getComments(long cardId) {

        List<Comment> commentList = commentRepository.findByCardIdWithUser(cardId);

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {

            CommentResponseDto commentResponseDto = new CommentResponseDto(
                    comment.getId(),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );

            commentResponseDtoList.add(commentResponseDto);

        }

        return commentResponseDtoList;

    }

    @Transactional
    public CommentResponseDto updateComment(long cardId, long commentId,
                                            CommentRequestDto commentRequestDto,
                                            AuthUser authUser) {

        findCardById(cardId);
        Comment comment = findCommentById(commentId);
        User user = findUserById(authUser.getId());
        commentUserMatch(comment,user.getId());
//        checkUserRole(user);

        comment.updateComment(commentRequestDto.getContent());

        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );

    }

    @Transactional
    public void deleteComment(long cardId, long commentId, AuthUser authUser) {

        findCardById(cardId);
        Comment comment = findCommentById(commentId);
        User user = findUserById(authUser.getId());
        commentUserMatch(comment,user.getId());
//        checkUserRole(user);

        commentRepository.delete(comment);

    }

    private Card findCardById(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("해당 카드가 존재하지 않음"));

        return card;
    }

    private Comment findCommentById(Long commentId){
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        return comment;
    }

    private User findUserById(Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);

        return user;
    }

    private void commentUserMatch(Comment comment, Long userId) {

        if ((comment.getUser() == null) || !ObjectUtils.nullSafeEquals(comment.getUser().getId(), userId)) {
            throw new IllegalArgumentException("해당 댓글과 사용자 일치하지 않음");
        }
    }

    // 유저의 역할 확인 읽기 전용일 경우 예외처리
    private void checkUserRole(User user, Long workspaceId) {

        WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId,user.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 해당 워크스페이스에 존재하지 않음"));

        if (workspaceUser.getRole().equals(WorkspaceRole.READ_ONLY)){
            throw new IllegalArgumentException("권한이 없습니다");
        }
    }

    // 이모지 구분
    private List<Emoji> emojis(String content){
        List<Emoji> emojis = new ArrayList<>();

        Pattern emojiPattern = Pattern.compile(":(\\w+):");
        Matcher emojiMatcher = emojiPattern.matcher(content);

        while (emojiMatcher.find()) {
            String emojiCode = emojiMatcher.group();
            Emoji emoji = new Emoji(); // 이 부분 수정 필요

            if (emoji != null) {
                emojis.add(emoji);
            }
        }

        return emojis;

    }

}
