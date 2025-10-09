
package com.sinan.hegsHaber.service.social;

import com.sinan.hegsHaber.entity.social.Badge;
import com.sinan.hegsHaber.repository.social.BadgeRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;

    public Badge saveBadge(Badge badge) {
        return badgeRepository.save(badge);
    }

    public Badge getBadgeById(Long id) {
        return badgeRepository.findById(id).orElse(null);
    }

    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    public void deleteBadge(Long id) {
        badgeRepository.deleteById(id);
    }

    public List<Badge> getBadgesByUserId(UUID userId) {
        return badgeRepository.findByUser_Id(userId);
    }

}
