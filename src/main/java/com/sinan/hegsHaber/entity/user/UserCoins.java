package com.sinan.hegsHaber.entity.user;

import java.time.Instant;
import java.util.UUID;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_coins")
public class UserCoins {
    @Id
    private Long id;

    @Column(name = "user_uuid")
    private UUID userUuid;

    // Relationship to User using user_uuid; keep primitive field for flexibility
    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
    private User user;
    @Column(name = "balance")
    private int balance;
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @PrePersist
    public void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }
}
