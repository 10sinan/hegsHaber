package com.sinan.hegsHaber.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class FriendshipDto {
    private UUID id;
    private String username;
    private String createdAt;
}
