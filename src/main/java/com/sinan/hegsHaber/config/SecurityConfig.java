package com.sinan.hegsHaber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration // Güvenlik ayarları config
public class SecurityConfig {

    @Bean // Sifreleri hashlemek icin
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain apiFiltrele(HttpSecurity http) throws Exception {// Güvenlik filtre zinciri ayarlari
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable()) // CORS ayarlari asagida yapilacak ama suan devre disi
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login", "/api/heartbeat", "/news/**")
                        .permitAll()// Bu endpointlere herkes erisebilir
                        .anyRequest().authenticated());

        return http.build();
    }

    // CORS için config
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://hegs.com.tr")); // frontend adresi
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // Cookie / Authorization header kullanılacaksa gerekli

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
