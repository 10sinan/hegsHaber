package com.sinan.hegsHaber.repository.social;

import com.sinan.hegsHaber.entity.social.Badge;
import com.sinan.hegsHaber.entity.user.User;
import com.sinan.hegsHaber.entity.user.UserBadge;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    List<UserBadge> findByUser_Id(UUID userId);

    List<UserBadge> findByBadge(Badge badge);

    boolean existsByUserAndBadge(User user, Badge badge);
}
