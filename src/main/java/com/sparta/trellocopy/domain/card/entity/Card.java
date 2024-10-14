package com.sparta.trellocopy.domain.card.entity;

import com.sparta.trellocopy.domain.common.entity.Timestamped;
import com.sparta.trellocopy.domain.list.entity.List;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
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

    @OneToMany(mappedBy = "card")
    private ArrayList<List> list;

    private String file_url;

}
