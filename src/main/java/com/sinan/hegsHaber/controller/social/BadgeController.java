package com.sinan.hegsHaber.controller.social;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import com.sinan.hegsHaber.entity.social.Badge;
import com.sinan.hegsHaber.service.social.BadgeService;
import com.sinan.hegsHaber.entity.user.User;
import com.sinan.hegsHaber.repository.user.UserRepository;
import com.sinan.hegsHaber.dto.social.BadgeDto;
import com.sinan.hegsHaber.mapper.BadgeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/badges")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;
    private final UserRepository userRepository;

    @PostMapping("/create-badge")
    public ResponseEntity<BadgeDto> createBadge(@RequestBody Badge badge) {// başarım oluşturma
        Badge savedBadge = badgeService.saveBadge(badge);
        BadgeDto dto = BadgeMapper.toBadgeDto(savedBadge);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/user/{userId}") // kullanıcıya ait başarımları getirme
    public ResponseEntity<List<BadgeDto>> getBadgesByUserId(@PathVariable UUID userId) {
        List<Badge> badges = badgeService.getBadgesByUserId(userId);
        List<BadgeDto> dtos = badges.stream().map(BadgeMapper::toBadgeDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BadgeDto>> getAllBadges() {// tüm başarımları getirme
        List<Badge> badges = badgeService.getAllBadges();
        List<BadgeDto> dtos = badges.stream().map(BadgeMapper::toBadgeDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping("/assign") // var olan badge'i kullanıcıya ata (user_badges)
    public ResponseEntity<BadgeDto> assignBadgeToUser(@RequestParam Long badgeId, @RequestParam UUID userId) {
        Badge badge = badgeService.getBadgeById(badgeId);
        if (badge == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);// badge bulunamadı
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);// kullanıcı bulunamadı
        }
        badgeService.assignBadgeToUser(user, badge);// kullanıcıya badge ata
        BadgeDto dto = BadgeMapper.toBadgeDto(badge);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

}
