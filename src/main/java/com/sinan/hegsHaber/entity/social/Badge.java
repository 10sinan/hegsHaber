package com.sinan.hegsHaber.entity.social;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "badges")

public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    // Not: Kullanıcı ilişkisi user_badges join tablosu ile yönetiliyor.

}
