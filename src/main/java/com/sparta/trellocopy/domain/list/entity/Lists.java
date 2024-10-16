package com.sparta.trellocopy.domain.list.entity;

import com.sparta.trellocopy.domain.board.entity.Board;
import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "lists")
@NoArgsConstructor
public class Lists extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String title;
    private Long orderNumber;

    @OneToMany(mappedBy = "lists",cascade = CascadeType.REMOVE)
    private List<Card> cardList = new ArrayList<>();


    public Lists(String title, Board board, Long orderNumber) {
        this.title = title;
        this.board = board;
        this.orderNumber = orderNumber;

    }

    public void update(Long neworderNumber) {
        this.orderNumber = neworderNumber;
    }
}
