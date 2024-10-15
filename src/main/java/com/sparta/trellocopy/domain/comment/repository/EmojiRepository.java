package com.sparta.trellocopy.domain.comment.repository;

import com.sparta.trellocopy.domain.comment.entity.Emoji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmojiRepository extends JpaRepository<Emoji, Long> {

}
