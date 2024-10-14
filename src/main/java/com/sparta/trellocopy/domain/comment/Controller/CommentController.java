package com.sparta.trellocopy.domain.comment.Controller;

import com.sparta.trellocopy.domain.comment.Dto.CommentRequestDto;
import com.sparta.trellocopy.domain.comment.Dto.CommentResponseDto;
import com.sparta.trellocopy.domain.comment.Dto.CommentSaveRequestDto;
import com.sparta.trellocopy.domain.comment.Dto.CommentSaveResponseDto;
import com.sparta.trellocopy.domain.comment.Service.CommentService;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/cards/{cardId}/comments")
    public ResponseEntity<CommentSaveResponseDto> createComment(
            @PathVariable Long cardId,
            @RequestBody CommentSaveRequestDto commentSaveRequestDto,
            @AuthenticationPrincipal AuthUser authUser) {
        CommentSaveResponseDto commentSaveResponseDto = commentService.createComment(
                cardId, commentSaveRequestDto, authUser);

    return ResponseEntity.ok(commentSaveResponseDto);
    }

    @GetMapping("/cards/{cardId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComment(
            @PathVariable Long cardId) {
        List<CommentResponseDto> comments = commentService.getComments(cardId);

        return ResponseEntity.ok(comments);
    }

    @PutMapping("/cards/{cardId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long cardId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal AuthUser authUser) {
        CommentResponseDto commentResponseDto = commentService.updateComment(
                cardId, commentId, commentRequestDto, authUser);

        return ResponseEntity.ok(commentResponseDto);
    }

    @DeleteMapping("/cards/{cardId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long cardId,
                              @PathVariable Long commentId,
                              @AuthenticationPrincipal AuthUser authUser) {
        commentService.deleteComment(cardId, commentId, authUser);
    }

}
