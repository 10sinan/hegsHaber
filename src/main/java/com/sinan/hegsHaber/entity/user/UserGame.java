package com.sinan.hegsHaber.entity.user;

import java.sql.Timestamp;
import java.util.UUID;

import com.sinan.hegsHaber.entity.social.Game;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user_games")
public class UserGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID userUuid;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    private String status;
    private Integer score;
    private Integer xpEarned;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    // Getter & Setter
}
