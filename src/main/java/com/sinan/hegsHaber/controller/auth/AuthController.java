package com.sinan.hegsHaber.controller.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sinan.hegsHaber.dto.auth.AuthResponse;
import com.sinan.hegsHaber.dto.auth.LoginRequestDTO;
import com.sinan.hegsHaber.dto.auth.RegisterRequestDTO;
import com.sinan.hegsHaber.service.user.AuthService;
import com.sinan.hegsHaber.util.JwtUtil;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/auth")
// Kimlik dogrulama islemlerinin yeri
public class AuthController {

    @Autowired
    private AuthService authService;//

    @Autowired
    private JwtUtil jwtUtil;// JWT olusturma ve dogrulama islemleri

    // Kullanicı kayit endpoint'i
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequestDTO request) {// Kayit istegi al
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));// Basarili ise 201 don
    }

    // Kullanicı giris endpoint'i
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDTO request, HttpServletResponse response) {

        // JWT token'ı cookie olarak ekle
        AuthResponse body = authService.login(request);// Giris istegi al
        if (body != null && body.getToken() != null) {
            String jwtToken = body.getToken();
            Cookie cookie = new Cookie("jwt", jwtToken);
            cookie.setHttpOnly(true); // JavaScript erişemesin
            cookie.setPath("/"); // Tüm path'lerde geçerli olsun
            cookie.setMaxAge(60 * 60 * 24 * 30); // 1 ay geçerli
            response.addCookie(cookie);
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // Kullanicı cıkıs endpoint'i
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        return ResponseEntity.status(HttpStatus.OK).body("Çıkış işlemi tamamlandı");
    }
}
