package com.sinan.hegsHaber.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sinan.hegsHaber.dto.AuthResponse;
import com.sinan.hegsHaber.dto.LoginRequestDTO;
import com.sinan.hegsHaber.dto.RegisterRequestDTO;
import com.sinan.hegsHaber.service.AuthService;
import com.sinan.hegsHaber.util.JwtUtil;

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
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequestDTO request) {// Kayit istegi al
        AuthResponse response = authService.register(request);// Kayit islemini servise devret
        return ResponseEntity.status(201).body(response);// Kayit sonucunu 201 ile don
    }

    // Kullanicı giris endpoint'i
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDTO request) {// Giris istegi al
        AuthResponse response = authService.login(request);// Giris islemini servise devret
        return ResponseEntity.status(200).body(response);// Giris sonucunu don
    }
}
