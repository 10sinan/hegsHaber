package com.sinan.hegsHaber.entity.social;

import com.sinan.hegsHaber.entity.user.User;
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

    // Kullanıcı ilişkisi
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
