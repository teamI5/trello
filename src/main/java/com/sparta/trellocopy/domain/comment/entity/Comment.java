package com.sparta.trellocopy.domain.comment.entity;

import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.comment.Dto.CommentRequestDto;
import com.sparta.trellocopy.domain.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "card_id")
    private Card card;

    public Comment(String content, User user, Card card) {

        this.content = content;
        this.user = user;
        this.card = card;
    }

    public void updateComment(String content) {
        this.content = content;
    }

}
