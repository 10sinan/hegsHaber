package com.sinan.hegsHaber.entity.social;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Data
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;// oyun adı
    private String description;// acıklsmsa
    private Integer rewardPoints;// ödül puanı
    private Integer rewardXp;// ödül xp
    private Boolean isActive;// oyun aktif mi
    private String imageurl; // image url
    private Instant createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    @PrePersist
    // Instant demek anlık zaman
    public void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }

}
