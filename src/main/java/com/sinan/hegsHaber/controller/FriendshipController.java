package com.sinan.hegsHaber.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sinan.hegsHaber.entity.Friendship;
import com.sinan.hegsHaber.service.FriendshipService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/friendships")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;// Service bagla

    @PostMapping("/request")// Arkadaslik istegi gonder
    public ResponseEntity<Friendship> sendRequest(@RequestParam UUID fromUserId, @RequestParam UUID toUserId) {
        return ResponseEntity.ok(friendshipService.sendRequest(fromUserId, toUserId));
    }

    @PostMapping("/respond/{id}")// Arkadaslik istegine cevap ver
    public ResponseEntity<Friendship> respond(@PathVariable UUID id, @RequestParam boolean accept) {
        return ResponseEntity.ok(friendshipService.respondRequest(id, accept));
    }

    @GetMapping("/friends/{userId}")// Arkadas listesini getir
    public ResponseEntity<List<Friendship>> listFriends(@PathVariable UUID userId) {
        return ResponseEntity.ok(friendshipService.listFriends(userId));
    }

    @GetMapping("/incoming/{userId}")// Gelen arkadaslik isteklerini getir
    public ResponseEntity<List<Friendship>> incoming(@PathVariable UUID userId) {
        return ResponseEntity.ok(friendshipService.incomingPending(userId));
    }

    @GetMapping("/outgoing/{userId}")// Giden arkadaslik isteklerini getir
    public ResponseEntity<List<Friendship>> outgoing(@PathVariable UUID userId) {
        return ResponseEntity.ok(friendshipService.outgoingPending(userId));
    }
}
