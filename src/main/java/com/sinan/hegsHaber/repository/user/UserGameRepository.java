package com.sinan.hegsHaber.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinan.hegsHaber.entity.user.UserGame;

import java.util.List;
import java.util.UUID;

public interface UserGameRepository extends JpaRepository<UserGame, Long> {
    List<UserGame> findByUserUuid(UUID userUuid);
}
