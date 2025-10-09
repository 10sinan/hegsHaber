package com.sinan.hegsHaber.controller.social;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import com.sinan.hegsHaber.entity.social.Badge;
import com.sinan.hegsHaber.service.social.BadgeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/badges")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;

    @PostMapping("/create-badge")
    public ResponseEntity<Badge> createBadge(@RequestBody Badge badge) {// başarım oluşturma
        Badge savedBadge = badgeService.saveBadge(badge);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBadge);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Badge>> getBadgesByUserId(@PathVariable UUID userId) {
        List<Badge> badges = badgeService.getBadgesByUserId(userId);
        return ResponseEntity.ok(badges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Badge> getBadge(@PathVariable Long id) {
        Badge badge = badgeService.getBadgeById(id);
        if (badge != null) {
            return ResponseEntity.ok(badge);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Badge>> getAllBadges() {// tüm başarımları getirme
        List<Badge> badges = badgeService.getAllBadges();
        return ResponseEntity.status(HttpStatus.OK).body(badges);
    }

}
