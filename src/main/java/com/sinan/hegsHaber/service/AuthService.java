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
        if (userRepository.existsByUsername(username)) {// Kullanıcı adı zaten var mı kontrol et
            throw new RuntimeException("Kullanıcı adı zaten alınmış!");// hata fırlat
        }
        User user = new User();// Yeni kullanıcı oluştur
        user.setUsername(username);
        if (user.getUsername().equals("admin")) {
            user.setRole("ADMIN");// Eğer kullanıcı adı "admin" ise rolü "ADMIN" yap
        }

        // Şifreyi BCrypt ile hash'le
        user.setPassword(passwordEncoder.encode(password));// yani şifreyi hashle
        return userRepository.save(user);// veritabanına kaydet
    }

    // Kullanicı girisi
    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));// Kullanıcıyı bulamazsa hata fırlat
        // Girilen şifreyi BCrypt ile karşılaştır
        return passwordEncoder.matches(password, user.getPassword());// girilen şifre ile hashlenmiş şifreyi karşılaştır
    }
}
