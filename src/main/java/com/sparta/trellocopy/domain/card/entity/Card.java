package com.sparta.trellocopy.domain.card.entity;

import com.sparta.trellocopy.domain.common.entity.Timestamped;
import com.sparta.trellocopy.domain.user.entity.CardUser;
import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.sparta.trellocopy.domain.list.entity.Lists;

@Entity
@Getter
@NoArgsConstructor
public class Card extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String title;

    @Column(length = 100)
    private String contents;

    private LocalDateTime deadline;

    private String file_url;

    @OneToMany(mappedBy = "card")
    private List<CardUser> cardUsers = new ArrayList<>();



    public Card(String title, String contents, LocalDateTime deadline, String file_url, Lists lists){
        this.title = title;
        this.contents = contents;
        this.deadline = deadline;
        this.file_url = file_url;
        this.lists = lists;
    }

    public void update(String title, String contents, LocalDateTime deadline, String file_url) {
        this.title = title;
        this.contents = contents;
        this.deadline = deadline;
        this.file_url = file_url;
    }

    public void addCardUser(CardUser cardUser) {
        cardUsers.add(cardUser);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id",nullable = false)
    private Lists lists;
}
