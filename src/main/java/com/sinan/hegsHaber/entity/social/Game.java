package com.sinan.hegsHaber.entity.social;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;



@Entity
@Data
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;// oyun adı
    @Column(name = "description")
    private String description;// açıklama

    @Column(name = "reward_points")
    private Integer rewardPoints;// ödül puanı

    @Column(name = "reward_xp")
    private Integer rewardXp;// ödül xp

    @Column(name = "is_active")
    private Boolean isActive;// oyun aktif mi
    
    @Column(name = "image_url")
    private String image_url; // image url



}
