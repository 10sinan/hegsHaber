package com.sinan.hegsHaber.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinan.hegsHaber.entity.Game;
import com.sinan.hegsHaber.entity.User;
import com.sinan.hegsHaber.repository.GameRepository;
import com.sinan.hegsHaber.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Transactional
    public Game createGame(UUID ownerId, String name) {
        User owner = userRepository.findById(ownerId).orElseThrow(() -> new IllegalArgumentException("Kullanici yok"));
        Game g = new Game();
        g.setOwner(owner);
        g.setName(name);
        g.setScore(0);
        g.setLevel(1);
        g.setMetadata(null);
        return gameRepository.save(g);
    }

    public List<Game> listByUser(UUID ownerId) {
        User owner = userRepository.findById(ownerId).orElseThrow(() -> new IllegalArgumentException("Kullanici yok"));
        return gameRepository.findByOwner(owner);
    }

    @Transactional
    public Game updateScore(UUID gameId, Integer score, Integer level) {
        Game g = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("Oyun yok"));
        if (score != null) {
            g.setScore(score);
        }
        if (level != null) {
            g.setLevel(level);
        }
        return gameRepository.save(g);
    }
}
