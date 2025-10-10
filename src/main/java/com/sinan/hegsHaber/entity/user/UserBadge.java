package com.sinan.hegsHaber.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

import com.sinan.hegsHaber.entity.social.Badge;

@Entity
@Data
@Table(name = "user_badges")
public class UserBadge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "badge_id", referencedColumnName = "id")
    private Badge badge;

    @Column(name = "created_at")
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null)
            createdAt = Instant.now();
    }
}
