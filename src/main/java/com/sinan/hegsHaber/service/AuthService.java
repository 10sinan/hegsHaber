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
        User user = userRepository.findByEmail(request.getEmail());// Kullanıcıyı e-posta ile bul
        // Girilen şifreyi BCrypt ile karşılaştır
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {// girilen şifre ile hashlenmiş şifreyi
                                                                                 // karşılaştır
            String token = jwtUtil.tokenUret(user);// JWT oluştur
            return new AuthResponse("Giriş başarılı!", token);// Giris basarili
        }
        return new AuthResponse("Geçersiz kullanıcı adı veya şifre!", null);// Giris basarisiz
    }
}
