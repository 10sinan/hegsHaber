package com.sinan.hegsHaber.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_social_media")
public class User_social_media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    @ManyToOne// ManyToOne iliskisi, bir kullanicinin birden fazla sosyal medya hesabina sahip olabilecegini belirtir.
    @JoinColumn(name = "user_id")// Foreign key kolonu için 
    private User user;
}
