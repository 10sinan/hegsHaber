package com.sinan.hegsHaber.repository.user;

import com.sinan.hegsHaber.entity.user.UserXp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserXpRepository extends JpaRepository<UserXp, Long> {
    Optional<UserXp> findByUserUuid(UUID userUuid);
}
