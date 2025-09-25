package com.sinan.hegsHaber.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_status")
public class User_status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;// kullanici id

    @Column(nullable = false)
    private String status;// kullanici durumu (aktif, pasif, yasakli vb.)

}
