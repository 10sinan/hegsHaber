package com.sinan.hegsHaber.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sinan.hegsHaber.entity.Friendship;
import com.sinan.hegsHaber.entity.Friendship.Status;
import com.sinan.hegsHaber.entity.User;
import com.sinan.hegsHaber.repository.FriendshipRepository;
import com.sinan.hegsHaber.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor// Constructor injection icin
public class FriendshipService {// Arkadaslik islemlerini yapar

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    //@Transactional Metodun içindeki tüm veritabanı işlemleri başarılı olursa, değişiklikler kalıcı olur basarısısz olur sa geri alınır
    @Transactional
    public Friendship sendRequest(UUID fromUserId, UUID toUserId) {// Arkadaslik istegi gonder
        if (fromUserId.equals(toUserId)) {// Kendi kendine istek gonderemezsin
            throw new IllegalArgumentException("Kendine istek gonderemezsin");
        }
        User from = userRepository.findById(fromUserId)// Gonderen kullanici
                .orElseThrow(() -> new IllegalArgumentException("Gonderen kullanici yok"));
        User to = userRepository.findById(toUserId)// Hedef kullanici
                .orElseThrow(() -> new IllegalArgumentException("Hedef kullanici yok"));

        if (friendshipRepository.existsAnyDirection(from, to)) {// Zaten bir arkadaslik veya istek var mi
            throw new IllegalStateException("Zaten bir arkadaslik veya istek var");
        }
        Friendship f = new Friendship();// Yeni arkadaslik istegi olustur
        f.setRequester(from);
        f.setReceiver(to);
        f.setStatus(Status.PENDING);
        return friendshipRepository.save(f);
    }

    @Transactional
    public Friendship respondRequest(UUID requestId, boolean accept) {// Arkadaslik istegine cevap ver
        Friendship f = friendshipRepository.findById(requestId)// Arkadaslik istegi bul
                .orElseThrow(() -> new IllegalArgumentException("Istek yok"));
        if (f.getStatus() != Status.PENDING) {
            throw new IllegalStateException("Istek zaten cevaplanmis");
        }
        f.setStatus(accept ? Status.ACCEPTED : Status.REJECTED);// Durumu guncelle kabul veya reddet
        f.setRespondedAt(java.time.Instant.now());
        return friendshipRepository.save(f);// Guncellenmis arkadaslik istegini kaydet
    }

    public List<Friendship> listFriends(UUID userId) {// Arkadas listesini getir
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Kullanici yok"));
        return friendshipRepository.findAllByUserAndStatus(u, Status.ACCEPTED);// Kabul edilmis arkadasliklari getir
    }

    public List<Friendship> incomingPending(UUID userId) {// Gelen bekleyen arkadaslik isteklerini getir
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Kullanici yok"));
        return friendshipRepository.findByReceiverAndStatus(u, Status.PENDING);// Bekleyen istekleri getir
    }

    public List<Friendship> outgoingPending(UUID userId) {// Giden bekleyen arkadaslik isteklerini getir
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Kullanici yok"));
        return friendshipRepository.findByRequesterAndStatus(u, Status.PENDING);
    }
}
