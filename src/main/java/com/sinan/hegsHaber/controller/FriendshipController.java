package com.sinan.hegsHaber.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sinan.hegsHaber.dto.FriendshipDto;
import com.sinan.hegsHaber.service.FriendshipService;
import com.sinan.hegsHaber.mapper.FriendshipMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/friendships")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;
    private final FriendshipMapper friendshipMapper;

    @PostMapping("/request")
    public ResponseEntity<FriendshipDto> sendRequest(@RequestParam UUID fromUserId, @RequestParam UUID toUserId) {
        var entity = friendshipService.sendRequest(fromUserId, toUserId);
        return ResponseEntity.ok(friendshipMapper.toDto(entity));
    }

    @PostMapping("/respond/{id}")
    public ResponseEntity<FriendshipDto> respond(@PathVariable UUID id, @RequestParam boolean accept) {
        var entity = friendshipService.respondRequest(id, accept);
        return ResponseEntity.status(200).body(friendshipMapper.toDto(entity));
    }

    @GetMapping("/friends/{userId}")
    public ResponseEntity<List<FriendshipDto>> listFriends(@PathVariable UUID userId) {
        var entities = friendshipService.listFriends(userId);
        var dtos = entities.stream().map(friendshipMapper::toDto).toList();
        return ResponseEntity.status(200).body(dtos);
    }
}
