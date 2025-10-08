package com.sinan.hegsHaber.controller.social;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sinan.hegsHaber.service.FriendshipService;
import com.sinan.hegsHaber.dto.relationship.FriendshipDto;
import com.sinan.hegsHaber.mapper.FriendshipMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/friendships")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;//
    private final FriendshipMapper friendshipMapper;// DTO-Entity dönüşümleri için

    @PostMapping("/follow") // Takip etme işlemi
    public ResponseEntity<FriendshipDto> follow(@RequestParam UUID followerId, @RequestParam UUID followingId) {
        var response = friendshipService.follow(followerId, followingId);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(friendshipMapper.toDto(response.getBody()));
        }
        return ResponseEntity.status(response.getStatusCode()).build();// Başarısız ise boş yanıt döndür
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<FriendshipDto>> list(@PathVariable UUID userId) {
        var entities = friendshipService.listFollows(userId);// Kullanıcının takip ettiği kişileri listele
        var dtos = entities.stream().map(f -> friendshipMapper.toDto(f, userId)).toList();// Entity'leri DTO'lara
        return ResponseEntity.status(HttpStatus.OK).body(dtos);// DTO listesini döndür
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<Void> unfollow(@RequestParam UUID followerId, @RequestParam UUID followingId) {
        return friendshipService.unfollow(followerId, followingId);
    }
}
