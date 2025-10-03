package com.sinan.hegsHaber.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sinan.hegsHaber.entity.User;

import javax.crypto.SecretKey;
import java.util.Date;

@Component // JWT olusturma ve dogrulama islemleri icin yardımcı sinif newlemeye gerek yok

public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    private final long EXPIRATION = 1000 * 60 * 60; // 1 saat jwt suresi

    public String tokenUret(User user) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("email", user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    public String kullanıcıAdıCıkar(String token) {// JWT'den kullanici adini cikar
        return tokenİcerigiAl(token).getSubject();// Konuyu al yani kullanici adini al
    }

    public boolean tokenDogrula(String token, String username) {// Tokeni ve kullanici adini dogrula
        String tokenUsername = kullanıcıAdıCıkar(token);// JWT'den kullanici adini al
        return (tokenUsername.equals(username) && !tokenSureKontrol(token));// Kullanici adi eslesiyor mu ve token
                                                                            // suresi dolmamis mi
    }

    public boolean tokenSureKontrol(String token) {// Token suresinin dolup dolmadigini kontrol et
        return tokenİcerigiAl(token).getExpiration().before(new Date());// Suresi dolmus mu burayı anlamadim gpt
                                                                        // baktim
    }

    private Claims tokenİcerigiAl(String token) {// JWT icerigini al
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        JwtParser parser = Jwts.parser().verifyWith(key).build();// JWT ayrıştırıcı yani parse ediyoruz
        Jws<Claims> jws = parser.parseSignedClaims(token);// imzali icerigi ayrıştır
        return jws.getPayload();// icerigi dondur
    }
}
