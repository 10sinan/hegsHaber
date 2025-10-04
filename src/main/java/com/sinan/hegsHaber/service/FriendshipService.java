package com.sinan.hegsHaber.service;

import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sinan.hegsHaber.entity.Friendship;
import com.sinan.hegsHaber.entity.User;
import com.sinan.hegsHaber.repository.FriendshipRepository;
import com.sinan.hegsHaber.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Constructor injection için
public class FriendshipService {// Arkadaslik islemlerini yapar

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    // @Transactional Metodun içindeki tüm veritabanı işlemleri başarılı olursa,
    // değişiklikler kalıcı olur basarısısz olur sa geri alınır
    @Transactional
    public ResponseEntity<Friendship> follow(UUID followerId, UUID followingId) {
        if (followerId.equals(followingId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        User follower = userRepository.findById(followerId)
                .orElse(null);// Kullanici yoksa null 
        User following = userRepository.findById(followingId)
                .orElse(null);// Kullanici yoksa null 
        if (follower == null || following == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();// Kullanici yoksa 404 doner
        }
        if (friendshipRepository.existsAnyDirection(follower, following)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();// Zaten takip ediliyorsa 409 doner
        }
        Friendship f = new Friendship();
        f.setFollower(follower);
        f.setFollowing(following);
        Friendship saved = friendshipRepository.save(f);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);// 201 Created doner
    }

    public List<Friendship> listFollows(UUID userId) {
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Kullanici yok"));
        return friendshipRepository.findByFollower(u);
    }

    public ResponseEntity<Void> unfollow(UUID followerId, UUID followingId) {
        User follower = userRepository.findById(followerId)
                .orElse(null);
        User following = userRepository.findById(followingId)
                .orElse(null);
        if (follower == null || following == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Friendship friendship = friendshipRepository.findByFollowerAndFollowing(follower, following);
        if (friendship == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        friendshipRepository.delete(friendship);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
