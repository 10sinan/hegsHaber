package com.sinan.hegsHaber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sinan.hegsHaber.entity.Security;
import com.sinan.hegsHaber.entity.user.User;
import com.sinan.hegsHaber.util.JwtUtil;
import com.sinan.hegsHaber.mapper.UserMapper;
import com.sinan.hegsHaber.repository.user.UserRepository;
import com.sinan.hegsHaber.dto.auth.AuthResponse;
import com.sinan.hegsHaber.dto.auth.LoginRequestDTO;
import com.sinan.hegsHaber.dto.auth.RegisterRequestDTO;
import com.sinan.hegsHaber.dto.auth.UserDto;

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

    @Autowired
    private UserCoinsService userCoinsService;

    // Kullanicı kaydi
    public AuthResponse register(RegisterRequestDTO request) {
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
        return new AuthResponse(userDto, "Kayıt başarılı", null, null);
    }

    // Kullanicı girisi

    public AuthResponse login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return new AuthResponse(null, "kullanıcı yok bulunamadı ", null, null);
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getSecurity().getPasswordHash())) {
            return new AuthResponse(null, "Giriş başarısız", null, null);
        }
        UserDto userDto = userMapper.toUserDTO(user);
        // Kullanıcının coin bakiyesini getir
        Integer balance = null;
        var coinsList = userCoinsService.getByUserId(user.getId());// UUID kullanarak getir
        if (coinsList != null && !coinsList.isEmpty()) {//
            balance = coinsList.get(0).getBalance();// İlk kaydın bakiyesini al
        }
        return new AuthResponse(userDto, "Giriş başarılı", null, balance);
    }
}
