package com.sparta.trellocopy.domain.comment.Service;

import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.repository.CardRepository;
import com.sparta.trellocopy.domain.comment.Dto.CommentRequestDto;
import com.sparta.trellocopy.domain.comment.Dto.CommentResponseDto;
import com.sparta.trellocopy.domain.comment.entity.Comment;
import com.sparta.trellocopy.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    public CommentResponseDto addComment(long cardId, CommentRequestDto commentRequestDto) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new InvalidParameterException("해당 카드가 존재하지 않음"));

        Comment comment = new Comment(
            commentRequestDto.getContent(),
            user,
            card
        );

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(
            savedComment.getContent()
        )

    }

}
