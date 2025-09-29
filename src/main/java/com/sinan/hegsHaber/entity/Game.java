package com.sinan.hegsHaber.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Basit oyun kaydi ornegi. Kullaniciya ait olabilir.
 * Icerik: skor, seviye, meta veriler, oyuncu vs.
 */
@Data
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner; // oyunu oynayan / sahibi

    @Column(nullable = false, length = 100)
    private String name; // oyun adi (ornek: "kelime_tahmin")

    private Integer score; // skor

    private Integer level; // seviye

    @Column(length = 500)
    private String metadata; // json veya ekstra bilgi stringi

    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
