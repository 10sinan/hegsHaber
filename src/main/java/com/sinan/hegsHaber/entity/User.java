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

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(nullable = false, unique = true, length = 50)
    public String username;

    @Column(nullable = false, unique = true, length = 150)
    public String email;

    public Instant deletedAt;
    public Instant createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public Security security;// kullanici guvenlik bilgileri

    // Arkadaslik (giden istekler)
    @OneToMany(mappedBy = "requester")
    public List<Friendship> sentFriendRequests;

    // Arkadaslik (gelen istekler)
    @OneToMany(mappedBy = "receiver")
    public List<Friendship> receivedFriendRequests;

    // Kullanıcının oyun kayıtları
    @OneToMany(mappedBy = "owner")
    public List<Game> games;

    @Transient
    public int getFriendCount() {
        int accepted = 0;
        if (sentFriendRequests != null) {
            accepted += sentFriendRequests.stream().filter(f -> f.getStatus() == Friendship.Status.ACCEPTED).count();
        }
        if (receivedFriendRequests != null) {
            accepted += receivedFriendRequests.stream().filter(f -> f.getStatus() == Friendship.Status.ACCEPTED)
                    .count();
        }
        return accepted;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
