package com.sinan.hegsHaber.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class FriendshipDto {
    private UUID id;
    private String requesterUsername;
    private String receiverUsername;
    private String status;
    private String createdAt;
    private String respondedAt;
}
