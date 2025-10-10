package com.sinan.hegsHaber.service.user;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinan.hegsHaber.dto.social.Leaderboard;
import com.sinan.hegsHaber.entity.user.UserGame;
import com.sinan.hegsHaber.entity.user.UserXp;
import com.sinan.hegsHaber.entity.social.Game;
import com.sinan.hegsHaber.repository.user.UserGameRepository;
import com.sinan.hegsHaber.repository.user.UserRepository;
import com.sinan.hegsHaber.repository.user.UserXpRepository;

@Service
public class UserGameService {
    public void assignGameToUser(UUID userId, Long gameId) {// kullaniciya oyun atama
        UserGame userGame = new UserGame();
        userGame.setUserUuid(userId);
        Game game = new Game();
        game.setId(gameId);
        userGame.setGame(game);
        userGameRepository.save(userGame);
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserGameRepository userGameRepository;

    @Autowired
    private UserXpRepository userXpRepository;

    public List<Leaderboard> getLeaderboardByTotalXp() {
        // Toplam liderlik artık user_xp tablosundaki değerlere göre hesaplanacak
        List<UserXp> allUserXp = userXpRepository.findAll();
        Map<UUID, Leaderboard> map = new HashMap<>();
        for (UserXp ux : allUserXp) {
            if (ux.getUserUuid() == null)
                continue;
            Leaderboard entry = map.getOrDefault(ux.getUserUuid(), new Leaderboard());
            entry.setUserUuid(ux.getUserUuid());
            entry.setTotalXp(ux.getXp() != null ? ux.getXp() : 0);
            userRepository.findById(ux.getUserUuid())
                    .ifPresent(user -> entry.setUsername(user.getUsername()));
            map.put(ux.getUserUuid(), entry);
        }
        return map.values().stream()
                .sorted((a, b) -> Integer.compare(b.getTotalXp(), a.getTotalXp()))
                .limit(10)
                .toList();
    }

    public List<Leaderboard> getLeaderboardByGameXp(Long gameId) {
        List<UserGame> allGames = userGameRepository.findAll();
        Map<UUID, Leaderboard> map = new HashMap<>();
        for (UserGame ug : allGames) {
            if (ug.getGame() != null && ug.getGame().getId().equals(gameId)) {
                Leaderboard entry = map.getOrDefault(ug.getUserUuid(), new Leaderboard());
                entry.setUserUuid(ug.getUserUuid());
                entry.setGameXp(entry.getGameXp() + (ug.getXpEarned() != null ? ug.getXpEarned() : 0));
                userRepository.findById(ug.getUserUuid()).ifPresent(user -> entry.setUsername(user.getUsername()));
                map.put(ug.getUserUuid(), entry);
            }
        }
        // Sıralama ve ilk 10'u döndür
        return map.values().stream()
                .sorted((a, b) -> Integer.compare(b.getGameXp(), a.getGameXp()))
                .limit(10)
                .toList();
    }

    public List<UserGame> getUserGames(UUID userUuid) {
        return userGameRepository.findByUserUuid(userUuid);
    }

    @Transactional // tek bir işlem olarak çalıştır
    public void addXpToUserGame(Long userGameId, int xp) {// belirli bir kullanıcı oyununa xp ekle
        userGameRepository.findById(userGameId).ifPresent(userGame -> {// kullanıcı oyunu bulunursa
            int currentXp = userGame.getXpEarned() != null ? userGame.getXpEarned() : 0;
            userGame.setXpEarned(currentXp + xp);
            userGameRepository.save(userGame);
            // Toplam kullanıcı XP'sini de güncelle
            upsertAndIncrementUserXp(userGame.getUserUuid(), xp);
        });
    }

    @Transactional
    public void addXpToUser(UUID userId, int xp) {
        // Genel amaçlı: toplam kullanıcı XP'sine doğrudan ekleme
        upsertAndIncrementUserXp(userId, xp);
    }

    private void upsertAndIncrementUserXp(UUID userUuid, int delta) {// kullanıcı xp sini güncelle veya ekle
        UserXp userXp = userXpRepository.findByUserUuid(userUuid).orElseGet(() -> {
            UserXp ux = new UserXp();
            ux.setUserUuid(userUuid);
            ux.setXp(0);
            return ux;
        });
        int current = userXp.getXp() != null ? userXp.getXp() : 0;
        userXp.setXp(current + delta);
        userXpRepository.save(userXp);
    }

    public int getTotalXp(UUID userUuid) {// kullanıcının toplam xp sini döner
        return userXpRepository.findByUserUuid(userUuid)
                .map(UserXp::getXp)
                .orElse(0);
    }
}