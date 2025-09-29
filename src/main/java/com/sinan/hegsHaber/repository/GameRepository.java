package com.sinan.hegsHaber.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinan.hegsHaber.entity.Game;
import com.sinan.hegsHaber.entity.User;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    List<Game> findByOwner(User owner);
}
