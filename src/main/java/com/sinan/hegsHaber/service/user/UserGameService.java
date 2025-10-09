package com.sinan.hegsHaber.service.user;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinan.hegsHaber.dto.social.Leaderboard;
import com.sinan.hegsHaber.entity.user.UserGame;
import com.sinan.hegsHaber.repository.user.UserGameRepository;
import com.sinan.hegsHaber.repository.user.UserRepository;

@Service
public class UserGameService {
    @Autowired
    private UserRepository userRepository;

    public List<Leaderboard> getLeaderboardByTotalXp() {
        List<UserGame> allGames = userGameRepository.findAll();// Tüm oyunları al
        Map<UUID, Leaderboard> map = new HashMap<>(); // Kullanıcıya göre liderlik tablosunu tutacak harita
        for (UserGame ug : allGames) {// Her bir kullanıcı oyunu için
            Leaderboard entry = map.getOrDefault(ug.getUserUuid(), new Leaderboard());// Kullanıcı için mevcut giriş yoksa yeni oluştur
            entry.setUserUuid(ug.getUserUuid());// Kullanıcı UUID'sini ayarla
            entry.setTotalXp(entry.getTotalXp() + (ug.getXpEarned() != null ? ug.getXpEarned() : 0));//     Toplam XP'yi güncelle
            userRepository.findById(ug.getUserUuid()).ifPresent(user -> entry.setUsername(user.getUsername()));// Kullanıcı adını ayarla
            map.put(ug.getUserUuid(), entry);// map e ekle
        }
        
        return map.values().stream()// map in değerlerini al ve don
                .sorted((a, b) -> Integer.compare(b.getTotalXp(), a.getTotalXp()))// azalan sırala
                .limit(10)//    max 10 kısı
                .toList();// liste halınde
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

    @Autowired
    private UserGameRepository userGameRepository;

    public List<UserGame> getUserGames(UUID userUuid) {
        return userGameRepository.findByUserUuid(userUuid);
    }
}