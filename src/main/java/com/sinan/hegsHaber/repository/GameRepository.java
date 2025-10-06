package com.sinan.hegsHaber.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinan.hegsHaber.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}
