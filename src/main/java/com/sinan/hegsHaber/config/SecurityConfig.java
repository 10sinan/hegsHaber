package com.sinan.hegsHaber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

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
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/auth/register", "/auth/login", "/api/heartbeat", "/news/**")
                        .permitAll()// Bu endpointlere herkes erisebilir
                        .anyRequest().authenticated());

        return http.build();
    }

    /*
     * // CORS için config
     * 
     * @Bean
     * CorsConfigurationSource corsConfigurationSource() {
     * CorsConfiguration configuration = new CorsConfiguration();
     * configuration.setAllowedOriginPatterns(List.of("*")); // her yerden istek
     * gelebilir
     * configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE",
     * "OPTIONS"));
     * configuration.setAllowedHeaders(List.of("*"));
     * configuration.setAllowCredentials(true);
     * configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));
     * configuration.setMaxAge(3600L);
     * 
     * UrlBasedCorsConfigurationSource source = new
     * UrlBasedCorsConfigurationSource();
     * source.registerCorsConfiguration("/**", configuration);
     * return source;
     * }
     */
}