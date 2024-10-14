package com.sparta.trellocopy.domain.comment.Service;

import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.repository.CardRepository;
import com.sparta.trellocopy.domain.comment.Dto.CommentRequestDto;
import com.sparta.trellocopy.domain.comment.Dto.CommentResponseDto;
import com.sparta.trellocopy.domain.comment.Dto.CommentSaveRequestDto;
import com.sparta.trellocopy.domain.comment.Dto.CommentSaveResponseDto;
import com.sparta.trellocopy.domain.comment.entity.Comment;
import com.sparta.trellocopy.domain.comment.repository.CommentRepository;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentSaveResponseDto createComment(long cardId,
                                                CommentSaveRequestDto commentSaveRequestDto,
                                                AuthUser authUser) {

        Card card = findCardById(cardId);

        User user = findUserById(authUser.getId());

        // 유저 역할 확인 필요 읽기 전용일 경우 에외처리

        Comment comment = new Comment(
            commentSaveRequestDto.getComment(),
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

        Card card = findCardById(cardId);

        Comment comment = findCommentById(commentId);

        User user = findUserById(authUser.getId());

        if ((comment.getUser() == null) || !ObjectUtils.nullSafeEquals(comment.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("유저 일치하지 않음");
        }

        // 유저 역할 확인 필요 읽기 전용일 경우 에외처리

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

        Comment comment = findCommentById(commentId);

        User user = findUserById(authUser.getId());

        if ((comment.getUser() == null) || !ObjectUtils.nullSafeEquals(comment.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("유저 일치하지 않음");
        }

        // 유저 역할 확인 필요 읽기 전용일 경우 에외처리

        commentRepository.delete(comment);

    }

    public Card findCardById(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NullPointerException("해당 카드가 존재하지 않음"));

        return card;
    }

    public Comment findCommentById(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException("해당 댓글이 존재하지 않음"));

        return comment;
    }

    public User findUserById(Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);

        return user;
    }

    public void commentUserMatch(Long commentId, Long userId) {
        Comment comment = findCommentById(commentId);

        if (!userId.equals(comment.getUser().getId())) {
            throw new IllegalArgumentException("해당 댓글과 사용자 일치하지 않음");
        }
    }

}
