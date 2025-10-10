package com.sinan.hegsHaber.entity.social;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tiles")
public class Tiles { // Haber Karo Resimleri

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price_coins", nullable = false)
    private Integer priceCoins;

}
