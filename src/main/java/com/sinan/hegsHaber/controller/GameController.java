package com.sinan.hegsHaber.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sinan.hegsHaber.entity.Game;
import com.sinan.hegsHaber.service.GameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<Game> create(@RequestParam UUID ownerId, @RequestParam String name) {
        return ResponseEntity.ok(gameService.createGame(ownerId, name));
    }

    @GetMapping("/user/{ownerId}")
    public ResponseEntity<List<Game>> list(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(gameService.listByUser(ownerId));
    }

    @PatchMapping("/{gameId}")
    public ResponseEntity<Game> update(@PathVariable UUID gameId,
            @RequestParam(required = false) Integer score,
            @RequestParam(required = false) Integer level) {
        return ResponseEntity.ok(gameService.updateScore(gameId, score, level));
    }
}
