package com.sinan.hegsHaber.repository.social;

import com.sinan.hegsHaber.entity.social.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    // Kullanıcı ilişkisi user_badges join tablosu üzerinden yönetiliyor.
    List<Badge> findByNameContainingIgnoreCase(String name);

}
