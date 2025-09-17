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
import com.sinan.hegsHaber.util.JwtUtil;
import com.sinan.hegsHaber.dto.AuthResponse;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/auth")
// Kimlik dogrulama islemlerinin yeri
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;// JWT olusturma ve dogrulama islemleri

    // Kullanicı kayit endpoint'i
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody DTORegisterRequest request) {
        User user = authService.register(request.getUsername(), request.getPassword());// Kullaniciyi kaydet
        return ResponseEntity.ok("Kullanici olusturuldu: " + user.getUsername());// Basarili yanit dondur
    }

    // Kullanicı giris endpoint'i
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DTOLoginRequest request) {
        boolean success = authService.login(request.getUsername(), request.getPassword());
        if (success) {
            String token = jwtUtil.tokenUret(request.getUsername());
            AuthResponse response = new AuthResponse("Giris basarili!", token);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Geçersiz kullanıcı adı veya şifre!");
    }
}
