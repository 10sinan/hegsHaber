package com.sinan.hegsHaber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinan.hegsHaber.repository.GameRepository;
import com.sinan.hegsHaber.entity.Game;

import java.util.List;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game createGame(Game game) {
        return gameRepository.save(game);
    }
}
