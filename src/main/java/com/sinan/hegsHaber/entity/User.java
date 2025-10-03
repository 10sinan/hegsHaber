package com.sinan.hegsHaber.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Security security;// kullanici guvenlik bilgileri

    // Takip ettiklerim (ben follower'ım) -> friends.follower_uuid
    @OneToMany(mappedBy = "follower")
    private List<Friendship> sentFriendRequests;

    // Beni takip edenler (ben following'im) -> friends.following_uuid
    @OneToMany(mappedBy = "following")
    private List<Friendship> receivedFriendRequests;

    @Transient // veritabanında saklama sadece hesapla (gpt)
    public int getFriendCount() {
        int total = 0;
        if (sentFriendRequests != null) {
            total += sentFriendRequests.size();
        }
        if (receivedFriendRequests != null) {
            total += receivedFriendRequests.size();
        }
        return total;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
