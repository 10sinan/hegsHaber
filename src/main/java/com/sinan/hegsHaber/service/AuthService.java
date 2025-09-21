package com.sinan.hegsHaber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sinan.hegsHaber.dto.DTORegisterRequest;
import com.sinan.hegsHaber.entity.User;
import com.sinan.hegsHaber.repository.UserRepository;
import com.sinan.hegsHaber.util.JwtUtil;
import com.sinan.hegsHaber.mapper.UserMapper;
import com.sinan.hegsHaber.dto.AuthResponse;
import com.sinan.hegsHaber.dto.DTOLoginRequest;

import lombok.Data;

@Service
@Data
// Kimlik dogrulama ve kullanici yonetimi icin servis
public class AuthService {

    @Autowired // Kullanici veritabani islemleri
    private UserRepository userRepository;

    @Autowired // Sifreleri hashlemek icin
    private PasswordEncoder passwordEncoder;

    @Autowired // DTO-Entity dönüşümleri için
    private UserMapper userMapper;

    @Autowired // JWT olusturma ve dogrulama islemleri
    private JwtUtil jwtUtil;

    // Kullanicı kaydi
    public AuthResponse register(DTORegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("E-posta zaten alınmış!");
        }
        User user = userMapper.dtoToUser(request);// DTO'dan entity'ye dönüşüm
        // Varsayılan rolü "USER" olarak ayarla, eğer e-posta "admin" ise rolü "ADMIN"
        // yap
        if (user.getEmail().equals("admin")) {
            user.setRole("ADMIN");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));// Sifreyi hashle
        User savedUser = userRepository.save(user);
        // Entity'den DTO'ya dönüşüm
        userMapper.userToAuthResponse(savedUser);// Mesaj ve token null olarak gelir
        return new AuthResponse("Kayıt başarılı!", null);// Kayit basarili
    }

    // Kullanicı girisi
    public AuthResponse login(DTOLoginRequest request) {
        boolean exists = userRepository.existsByEmail(request.getEmail());
        if (!exists) {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }
        User user = userRepository.findByEmail(request.getEmail());
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtil.tokenUret(user);
            // Kullanıcı bilgilerini consola yazdır
            System.out.println("Kullanıcı Bilgileri: ");
            System.out.println("ID: " + user.getId());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Role: " + user.getRole());
            // Kullanıcı bilgilerini frontend'e dön
            return new AuthResponse("Giriş başarılı! Kullanıcı: " + user.getUsername()
                    + ",   Email: " + user.getEmail() + ",  Rol: " + user.getRole(), token);
        }
        return new AuthResponse("Geçersiz kullanıcı adı veya şifre!", null);
    }
}
