package com.sinan.hegsHaber.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Data
@Table(name = "security")
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // DB: BIGSERIAL PRIMARY KEY

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "profile_status", length = 20)
    private String profileStatus; // PUBLIC / PRIVATE

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private User user;
}
