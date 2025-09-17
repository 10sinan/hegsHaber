package com.sinan.hegsHaber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sinan.hegsHaber.entity.User;
import com.sinan.hegsHaber.repository.UserRepository;

import lombok.Data;

@Service
@Data
// Kimlik dogrulama ve kullanici yonetimi icin servis
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Kullanicı kaydi
    public User register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Kullanıcı adı zaten alınmış!");
        }
        User user = new User();
        user.setUsername(username);
        // Şifreyi BCrypt ile hash'le
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    // Kullanicı girisi
    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        // Girilen şifreyi BCrypt ile karşılaştır
        return passwordEncoder.matches(password, user.getPassword());
    }
}
