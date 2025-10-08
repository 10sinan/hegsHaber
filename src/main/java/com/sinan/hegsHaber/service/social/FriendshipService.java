package com.sinan.hegsHaber.service.social;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinan.hegsHaber.entity.social.Friendship;
import com.sinan.hegsHaber.entity.user.User;
import com.sinan.hegsHaber.repository.social.FriendshipRepository;
import com.sinan.hegsHaber.repository.user.UserRepository;

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
        User follower = userRepository.findById(followerId).orElse(null);//
        User following = userRepository.findById(followingId).orElse(null);
        if (follower == null || following == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Her iki yönde de arkadaşlık var mı kontrol et
        boolean existsAB = friendshipRepository.existsAnyDirection(follower, following);
        boolean existsBA = friendshipRepository.existsAnyDirection(following, follower);
        if (existsAB || existsBA) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        // A->B
        Friendship f1 = new Friendship();
        f1.setFollower(follower);
        f1.setFollowing(following);
        Friendship saved1 = friendshipRepository.save(f1);
        // B->A
        Friendship f2 = new Friendship();
        f2.setFollower(following);
        f2.setFollowing(follower);
        // Sadece birini döndür (A->B)
        return ResponseEntity.status(HttpStatus.CREATED).body(saved1);
    }

    public List<Friendship> listFollows(UUID userId) {
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Kullanici yok"));
        List<Friendship> asFollower = friendshipRepository.findByFollower(u);
        List<Friendship> asFollowing = friendshipRepository.findByFollowing(u);
        List<Friendship> all = new ArrayList<>();
        all.addAll(asFollower);
        for (Friendship f : asFollowing) {
            if (all.stream().noneMatch(x -> x.getId().equals(f.getId()))) {
                all.add(f);
            }
        }
        return all;
    }

    public ResponseEntity<Void> unfollow(UUID followerId, UUID followingId) {
        User follower = userRepository.findById(followerId).orElse(null);
        User following = userRepository.findById(followingId).orElse(null);
        if (follower == null || following == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Friendship friendshipAB = friendshipRepository.findByFollowerAndFollowing(follower, following);
        Friendship friendshipBA = friendshipRepository.findByFollowerAndFollowing(following, follower);
        boolean found = false;
        if (friendshipAB != null) {
            friendshipRepository.delete(friendshipAB);
            found = true;
        }
        if (friendshipBA != null) {
            friendshipRepository.delete(friendshipBA);
            found = true;
        }
        if (!found) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
