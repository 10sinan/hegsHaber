package com.sinan.hegsHaber.entity;

import java.security.Timestamp;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    
    public String username;
    public String email;
    public String authProvider;
    public Timestamp deletedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)// OneToOne iliskisi, bir kullanicinin tek bir profile sahip olabilecegini belirtir.
    public Security security;// kullanici guvenlik bilgileri

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    public User_profiles profile;// kullanici profili

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)// OneToMany iliskisi, bir kullanicinin birden fazla sosyal medya hesabina sahip olabilecegini belirtir.
    public List<User_social_media> socialMedias;// kullanicinin sosyal medya hesaplari

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<User_subscription> subscriptions;// kullanicinin abonelikleri

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Payment> payments;// kullanicinin odemeleri

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<User_interests> interests;// kullanicinin ilgi alanlari

}
