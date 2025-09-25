package com.sinan.hegsHaber.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_profiles")
public class User_profiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;// kullanici id

    @Column
    private String bio;// kullanici biyografi

    @Column
    private String profile_picture_url;// kullanici profil resmi url

    @OneToOne
    @MapsId// Primary key olarak kullanici id'sini kullan
    @JoinColumn(name = "user_id")// Foreign key kolonu
    private User user;

}