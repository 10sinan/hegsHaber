package com.sinan.hegsHaber.repository.social;

import com.sinan.hegsHaber.entity.social.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findByUser_Id(UUID userId);
    
}
