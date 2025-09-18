package com.sinan.hegsHaber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Guvenlik ayarlari icin konfigürasyon sinifi
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {// Sifreleri hashlemek icin BCrypt kullanıyorum
        return new BCryptPasswordEncoder();
    }

    // HTTP guvenlik ayarlari
    @Bean
    SecurityFilterChain ApiFiltrele(HttpSecurity http) throws Exception {// Guvenlik zincirini yapılandır
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login", "/api/heartbeat", "/news/**").permitAll()// Kayit,
                                                                                                                // giris
                                                                                                                // ve
                                                                                                                // haber
                                                                                                                // endpointi
                                                                                                                // herkese
                                                                                                                // acik
                                                                                                                // olsun
                        .anyRequest().authenticated());
        return http.build();// Guvenlik zincirini olustur( build yani tamamla)
    }
}
