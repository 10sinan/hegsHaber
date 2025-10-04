package com.sinan.hegsHaber.service;

import java.util.List;
import java.util.UUID;
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
    public Friendship follow(UUID followerId, UUID followingId) {
        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("Kendini takip edemezsin");
        }
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Takip eden kullanici yok"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("Takip edilen kullanici yok"));
        if (friendshipRepository.existsAnyDirection(follower, following)) {
            throw new IllegalStateException("Zaten takip ilişkisi var");
        }
        Friendship f = new Friendship();
        f.setFollower(follower);
        f.setFollowing(following);
        return friendshipRepository.save(f);
    }

    public List<Friendship> listFollows(UUID userId) {
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Kullanici yok"));
        return friendshipRepository.findByFollower(u);
    }

    public void unfollow(UUID followerId, UUID followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Takip eden kullanici yok"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("Takip edilen kullanici yok"));
        Friendship friendship = friendshipRepository.findByFollowerAndFollowing(follower, following);
        if (friendship == null) {
            throw new IllegalArgumentException("Takip ilişkisi bulunamadı");
        }
        friendshipRepository.delete(friendship);
    }
}
