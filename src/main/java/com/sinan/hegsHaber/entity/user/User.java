package com.sinan.hegsHaber.entity.user;

import java.util.List;
import java.util.UUID;

import com.sinan.hegsHaber.entity.Security;
import com.sinan.hegsHaber.entity.social.Friendship;
import com.sinan.hegsHaber.entity.social.SavedNews;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Security security;// kullanici guvenlik bilgileri

    // Takip ettiklerim (ben follower'ım) -> friends.follower_uuid
    @OneToMany(mappedBy = "follower")
    private List<Friendship> sentFriendRequests;

    // Beni takip edenler (ben following'im) -> friends.following_uuid
    @OneToMany(mappedBy = "following")
    private List<Friendship> receivedFriendRequests;

    // Reverse relations (optional, for convenience)
    @OneToMany(mappedBy = "user")
    private List<UserCoins> coins;

    @OneToMany(mappedBy = "user")
    private List<UserPets> pets;

    @OneToMany(mappedBy = "user")
    private List<UserGame> games;

    @OneToMany(mappedBy = "user")
    private List<SavedNews> savedNews;

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

}
