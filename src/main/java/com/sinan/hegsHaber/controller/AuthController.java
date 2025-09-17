package com.sinan.hegsHaber.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sinan.hegsHaber.dto.DTOLoginRequest;
import com.sinan.hegsHaber.dto.DTORegisterRequest;
import com.sinan.hegsHaber.entity.User;
import com.sinan.hegsHaber.service.AuthService;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/auth")
// Kimlik dogrulama islemlerinin yeri
public class AuthController {

    @Autowired
    private AuthService authService;

    // Kullanicı kayit endpoint'i
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody DTORegisterRequest request) {
        User user = authService.register(request.getUsername(), request.getPassword());// Kullaniciyi kaydet
        return ResponseEntity.ok("Kullanici olusturuldu: " + user.getUsername());// Basarili yanit dondur
    }

    // Kullanicı giris endpoint'i
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DTOLoginRequest request) { // response entity demek http response dondur
                                                                           // demek
        boolean success = authService.login(request.getUsername(), request.getPassword());// Giris yapmayi dene
        if (success) {// Giris basariliysa
            return ResponseEntity.ok("Giris basarili!");// Basarili yanit dondur
        }
        return ResponseEntity.status(401).body("Geçersiz kullanıcı adı veya şifre!");// Geçersiz giriş yanıtı döndür
    }
}
