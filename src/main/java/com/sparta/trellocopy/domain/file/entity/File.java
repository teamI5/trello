package com.sparta.trellocopy.domain.file.entity;

import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "file")
public class File extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(nullable = false)
    private String uploadFileName;

    @Column(nullable = false)
    private String storeFileName;

    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private long fileSize;

    public File(Card card, String uploadFileName, String storeFileName, String fileUrl, String fileType, long fileSize) {
        this.card = card;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

}
