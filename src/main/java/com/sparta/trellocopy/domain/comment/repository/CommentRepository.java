package com.sparta.trellocopy.domain.comment.repository;

import com.sparta.trellocopy.domain.comment.entity.Comment;
import com.sparta.trellocopy.domain.common.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.card.id = :cardId")
    List<Comment> findByCardIdWithUser(@Param("cardId") Long cardId);

    default Comment findByIdOrElseThrow(Long commentId) {
        return findById(commentId).orElseThrow(() -> new NotFoundException("해당 댓글을 찾을 수 없음"));
    }

}
