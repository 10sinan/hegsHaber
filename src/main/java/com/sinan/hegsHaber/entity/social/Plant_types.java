package com.sinan.hegsHaber.entity.social;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "plant_types")
@Data
public class Plant_types {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "point_cost") // puan maliyeti
    private int point_cost;

    @Column(name = "image_url")
    private String image_url;
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @PrePersist
    public void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }
}
