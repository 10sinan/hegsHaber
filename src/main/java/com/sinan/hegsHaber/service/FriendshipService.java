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
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    @Transactional
    public Friendship sendRequest(UUID fromUserId, UUID toUserId) {
        if (fromUserId.equals(toUserId)) {
            throw new IllegalArgumentException("Kendine istek gonderemezsin");
        }
        User from = userRepository.findById(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("Gonderen kullanici yok"));
        User to = userRepository.findById(toUserId)
                .orElseThrow(() -> new IllegalArgumentException("Hedef kullanici yok"));

        if (friendshipRepository.existsAnyDirection(from, to)) {
            throw new IllegalStateException("Zaten bir arkadaslik veya istek var");
        }
        Friendship f = new Friendship();
        f.setRequester(from);
        f.setReceiver(to);
        f.setStatus(Status.PENDING);
        return friendshipRepository.save(f);
    }

    @Transactional
    public Friendship respondRequest(UUID requestId, boolean accept) {
        Friendship f = friendshipRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Istek yok"));
        if (f.getStatus() != Status.PENDING) {
            throw new IllegalStateException("Istek zaten cevaplanmis");
        }
        f.setStatus(accept ? Status.ACCEPTED : Status.REJECTED);
        f.setRespondedAt(java.time.Instant.now());
        return friendshipRepository.save(f);
    }

    public List<Friendship> listFriends(UUID userId) {
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Kullanici yok"));
        return friendshipRepository.findAllByUserAndStatus(u, Status.ACCEPTED);
    }

    public List<Friendship> incomingPending(UUID userId) {
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Kullanici yok"));
        return friendshipRepository.findByReceiverAndStatus(u, Status.PENDING);
    }

    public List<Friendship> outgoingPending(UUID userId) {
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Kullanici yok"));
        return friendshipRepository.findByRequesterAndStatus(u, Status.PENDING);
    }
}
