package com.sinan.hegsHaber.service;

import com.sinan.hegsHaber.dto.Leaderboard;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinan.hegsHaber.entity.UserGame;
import com.sinan.hegsHaber.repository.UserGameRepository;
import com.sinan.hegsHaber.repository.UserRepository;

@Service
public class UserGameService {
    @Autowired
    private UserRepository userRepository;

    public List<Leaderboard> getLeaderboardByTotalXp() {
        List<UserGame> allGames = userGameRepository.findAll();
        Map<UUID, Leaderboard> map = new HashMap<>();
        for (UserGame ug : allGames) {// usergame tablosundaki her kayıt için 
            Leaderboard entry = map.getOrDefault(ug.getUserUuid(), new Leaderboard());// kullanıcıya ait mevcut liderlik tablosu girdisini al veya yeni oluştur
            entry.setUserUuid(ug.getUserUuid());// kullanıcı uuid'sini ayarla
            entry.setTotalXp(entry.getTotalXp() + (ug.getXpEarned() != null ? ug.getXpEarned() : 0));// toplam xp'yi güncelle
            // Kullanıcı adını ekle
            userRepository.findById(ug.getUserUuid()).ifPresent(user -> entry.setUsername(user.getUsername()));
            map.put(ug.getUserUuid(), entry);
        }
        return new ArrayList<>(map.values());
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
        return new ArrayList<>(map.values());
    }

    @Autowired
    private UserGameRepository userGameRepository;

    public List<UserGame> getUserGames(UUID userUuid) {
        return userGameRepository.findByUserUuid(userUuid);
    }
}