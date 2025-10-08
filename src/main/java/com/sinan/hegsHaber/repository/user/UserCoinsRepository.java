package com.sinan.hegsHaber.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinan.hegsHaber.entity.user.UserCoins;

import java.util.UUID;
import java.util.List;

@Repository
public interface UserCoinsRepository extends JpaRepository<UserCoins, Long> {
    List<UserCoins> findByUserUuid(UUID userUuid);
}
