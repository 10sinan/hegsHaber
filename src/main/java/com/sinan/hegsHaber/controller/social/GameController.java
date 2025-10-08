package com.sinan.hegsHaber.controller.social;

import com.sinan.hegsHaber.entity.social.Game;
import com.sinan.hegsHaber.service.social.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/all")
    public ResponseEntity<List<Game>> getAllGames() {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getAllGames());//burası oyunların listesini döndürüyor.
    }
    @PostMapping("/create")
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        Game entity = gameService.createGame(game);        
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);//burası oyun oluşturuyor.
    }
    
}
