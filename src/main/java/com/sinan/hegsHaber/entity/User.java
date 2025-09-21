package com.sinan.hegsHaber.entity;

import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users") // users tablosu veritabaninda bir isim.
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "USER";// Varsayılan rol "USER"

    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;// kullanici email

    @Column(updatable = false) // olusturma zamani degistirilemez
    private Timestamp createdAt;// kullanici olusturma zamani

}
