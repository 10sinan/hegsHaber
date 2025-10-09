package com.sinan.hegsHaber.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

import com.sinan.hegsHaber.entity.social.Pet_types;

@Entity
@Data
@Table(name = "user_pets")
public class UserPets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_uuid")
    private UUID userUuid;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
    private User user;

    @Column(name = "pet_id")
    private Long petId;

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Pet_types pet;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
