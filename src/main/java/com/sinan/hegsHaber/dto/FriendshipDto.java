package com.sinan.hegsHaber.dto;

import lombok.Data;

@Data
public class FriendshipDto {
    private Long id;
    private String followerUsername;
    private String followingUsername;
    private String createdAt;
}
