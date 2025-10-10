
package com.sinan.hegsHaber.service.social;

import com.sinan.hegsHaber.entity.social.Badge;
import com.sinan.hegsHaber.entity.social.UserBadge;
import com.sinan.hegsHaber.entity.user.User;
import com.sinan.hegsHaber.repository.social.BadgeRepository;
import com.sinan.hegsHaber.repository.social.UserBadgeRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;

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
        return userBadgeRepository.findByUser_Id(userId)
                .stream()
                .map(UserBadge::getBadge)
                .toList();
    }

    public UserBadge assignBadgeToUser(User user, Badge badge) {// kullanıcıya badge ata
        // aynı badge'in aynı kullanıcıya birden fazla atanmasını önle
        if (userBadgeRepository.existsByUserAndBadge(user, badge)) {
            // zaten atanmışsa mevcut kaydı döndür
            return userBadgeRepository.findByBadge(badge)
                    .stream()
                    .filter(ub -> ub.getUser().getId().equals(user.getId()))
                    .findFirst()
                    .orElse(null);
        }
        UserBadge userBadge = new UserBadge();// yeni kullanıcı-başarım ilişkisi oluştur
        userBadge.setUser(user);
        userBadge.setBadge(badge);
        return userBadgeRepository.save(userBadge);
    }

}
