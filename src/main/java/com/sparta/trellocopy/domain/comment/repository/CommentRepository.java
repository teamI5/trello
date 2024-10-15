package com.sparta.trellocopy.domain.comment.repository;

import com.sparta.trellocopy.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
