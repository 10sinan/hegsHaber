package com.sinan.hegsHaber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sinan.hegsHaber.entity.Security;
import com.sinan.hegsHaber.entity.User;
import com.sinan.hegsHaber.repository.UserRepository;
import com.sinan.hegsHaber.util.JwtUtil;
import com.sinan.hegsHaber.mapper.UserMapper;
import com.sinan.hegsHaber.dto.AuthResponse;
import com.sinan.hegsHaber.dto.UserDto;
import com.sinan.hegsHaber.dto.LoginRequestDTO;
import com.sinan.hegsHaber.dto.RegisterRequestDTO;

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
    public ResponseEntity<AuthResponse> register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("E-posta zaten alınmış!");
        }
        User user = userMapper.toUser(request); // DTO'dan entity'ye dönüşüm

        Security security = new Security();
        security.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        security.setProfileStatus("PUBLIC"); // DB constraint'e uygun
        security.setUser(user);
        user.setSecurity(security);

        User savedUser = userRepository.save(user);
        UserDto userDto = userMapper.toUserDTO(savedUser);
        return ResponseEntity.status(201).body(new AuthResponse(userDto, "Kayıt başarılı", null));
    }

    // Kullanicı girisi
    public ResponseEntity<AuthResponse> login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.status(404).body(new AuthResponse(null, "kullanıcı yok bulunamadı ", null));
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getSecurity().getPasswordHash())) {
            return ResponseEntity.status(401).body(new AuthResponse(null, "Giriş başarısız", null));
        }
        UserDto userDto = userMapper.toUserDTO(user);
        // Token sadece cookie'ye yazılacak, response body'de dönmeyecek
        return ResponseEntity.ok(new AuthResponse(userDto, "Giriş başarılı", null));
    }
}
