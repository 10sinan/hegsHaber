package com.sinan.hegsHaber.controller.user;

import org.springframework.http.ResponseEntity;

import com.sinan.hegsHaber.dto.social.Leaderboard;
import com.sinan.hegsHaber.entity.user.UserGame;
import com.sinan.hegsHaber.service.user.UserGameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-games") // kullanici oyun bilgileri
public class UserGameController {

    @PostMapping("/assign") // kullaniciya oyun atama
    public ResponseEntity<String> assignGameToUser(@RequestParam UUID userId, @RequestParam Long gameId) {
        userGameService.assignGameToUser(userId, gameId);
        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }

    @Autowired
    private UserGameService userGameService;

    // Liderlik tablosu: toplam XP'ye göre
    @GetMapping("/leaderboard")
    public ResponseEntity<List<Leaderboard>> getLeaderboard(@RequestParam(required = false) Long gameId) {
        List<Leaderboard> leaderboard;
        if (gameId == null) {
            leaderboard = userGameService.getLeaderboardByTotalXp();// tüm oyunlar için toplam xp göre liderlik tablosu
        } else {
            leaderboard = userGameService.getLeaderboardByGameXp(gameId);// belirli bir oyun için xp göre liderlik
                                                                         // tablosu
        }
        if (leaderboard.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(leaderboard);// boş liste dönerse 404
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(leaderboard);// dolu liste dönerse 200
        }
    }

    @GetMapping("/{userUuid}") // belirli bir kullanıcının oyun bilgilerini döner
    public ResponseEntity<List<UserGame>> getUserGames(@PathVariable UUID userUuid) {
        List<UserGame> userGames = userGameService.getUserGames(userUuid);
        if (userGames.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userGames);// kullanıcı bulunamazsa 404
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userGames);// kullanıcı bulunursa 200
        }
    }

    @GetMapping("/total-xp/{userUuid}")
    public ResponseEntity<Integer> getTotalXp(@PathVariable UUID userUuid) {
        int total = userGameService.getTotalXp(userUuid);
        return ResponseEntity.status(HttpStatus.OK).body(total);
    }

    /**
     * userId ve gameId ile xp ekleme endpointi
     */
    @PostMapping("/add-xp-by-user-game")
    public ResponseEntity<String> addXpToUserGameByUserAndGameId(@RequestParam UUID userId, @RequestParam Long gameId,
            @RequestParam int xp) {
        userGameService.addXpToUserGameByUserAndGameId(userId, gameId, xp);
        return ResponseEntity.status(HttpStatus.OK).body("XP eklendi");
    }
}
