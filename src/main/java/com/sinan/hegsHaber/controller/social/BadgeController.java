package com.sinan.hegsHaber.controller.social;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Badge> getBadge(@PathVariable Long id) {// id ye gore başarım getirme
        Badge badge = badgeService.getBadgeById(id);
        if (badge != null) {
            return ResponseEntity.ok(badge);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
