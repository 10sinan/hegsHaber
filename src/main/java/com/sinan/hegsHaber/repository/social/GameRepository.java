package com.sinan.hegsHaber.repository.social;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinan.hegsHaber.entity.social.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}
