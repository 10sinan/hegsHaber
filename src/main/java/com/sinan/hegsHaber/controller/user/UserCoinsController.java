package com.sinan.hegsHaber.controller.user;

import com.sinan.hegsHaber.entity.user.UserCoins;
import com.sinan.hegsHaber.service.user.UserCoinsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-coins")
public class UserCoinsController {
    @Autowired
    private UserCoinsService userCoinsService;

    @PostMapping("/save")
    public ResponseEntity<UserCoins> saveUserCoins(@RequestBody UserCoins userCoins) {// kullanici coin kaydet
        UserCoins saved = userCoinsService.save(userCoins);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserCoins>> getUserCoins(@PathVariable UUID userId) { // kullaniciya ait coinleri getir
        List<UserCoins> coins = userCoinsService.getByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(coins);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserCoins>> getAllUserCoins() {
        List<UserCoins> all = userCoinsService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(all);// tum kullanici coinlerini getir 
    }
}
