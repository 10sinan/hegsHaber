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

    @PostMapping("/follow")
    public ResponseEntity<FriendshipDto> follow(@RequestParam UUID followerId, @RequestParam UUID followingId) {
        var entity = friendshipService.follow(followerId, followingId);
        return ResponseEntity.ok(friendshipMapper.toDto(entity));
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<FriendshipDto>> list(@PathVariable UUID userId) {
        var entities = friendshipService.listFollows(userId);
        var dtos = entities.stream().map(friendshipMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }
}
