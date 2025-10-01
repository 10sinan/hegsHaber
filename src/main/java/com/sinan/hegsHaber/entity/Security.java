package com.sinan.hegsHaber.entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "security") // security tablosu veritabaninda bir isim.

public class Security {
    @Id
    private java.util.UUID id; // User ile ayni UUID primary key

    private String passwordHash;// Sifrelenmis parola
    private String profileStatus;// Kullanici profil durumu (ornek: aktif, pasif, askida)

    @OneToOne
    @JsonIgnore
    @MapsId // Primary key olarak kullanici id'sini kullan
    @JoinColumn(name = "user_id")
    private User user;

}
