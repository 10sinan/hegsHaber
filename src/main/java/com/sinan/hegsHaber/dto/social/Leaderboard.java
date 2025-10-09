package com.sinan.hegsHaber.dto.social;

import java.util.UUID;
import lombok.Data;

@Data
public class Leaderboard {
    private UUID userUuid;
    private String username;
    private int totalXp;
    private int gameXp;
}
