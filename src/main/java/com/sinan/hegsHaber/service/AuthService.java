package com.sinan.hegsHaber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public AuthResponse register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("E-posta zaten alınmış!");
        }
        User user = userMapper.dtoToUser(request);// DTO'dan entity'ye dönüşüm
        // Varsayılan rolü "USER" olarak ayarla, eğer kullanıcı adı "admin" ise rolü "ADMIN" yap
        if (user.getUsername().equals("admin")) {
            user.setRole("ADMIN");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));// Sifreyi hashle
        User savedUser = userRepository.save(user);
        UserDto userDto = userMapper.userToUserDto(savedUser);
        return new AuthResponse(userDto, null);
    }

    // Kullanicı girisi
    public AuthResponse login(LoginRequestDTO request) {
        boolean exists = userRepository.existsByEmail(request.getEmail());
        if (!exists) {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }
        User user = userRepository.findByEmail(request.getEmail());
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            UserDto userDto = userMapper.userToUserDto(user);
            return new AuthResponse(userDto, "giris basarılı "); // Token dönülmüyor
        }
        return new AuthResponse(null, "Giriş başarısız"); // Giris basarisiz.
    }
}
