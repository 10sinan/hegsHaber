package com.sinan.hegsHaber.service;

import com.sinan.hegsHaber.entity.UserCoins;
import com.sinan.hegsHaber.repository.UserCoinsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserCoinsService {
    @Autowired
    private UserCoinsRepository userCoinsRepository;

    public UserCoins save(UserCoins userCoins) {
        return userCoinsRepository.save(userCoins);
    }

    public List<UserCoins> getByUserId(UUID userId) {
        return userCoinsRepository.findByUserUuid(userId);
    }

    public List<UserCoins> getAll() {
        return userCoinsRepository.findAll();
    }
}
