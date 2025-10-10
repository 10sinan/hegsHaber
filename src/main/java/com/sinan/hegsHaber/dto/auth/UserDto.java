package com.sinan.hegsHaber.dto.auth;

import java.util.UUID;

import lombok.Data;

@Data
public class UserDto {
    private UUID id;
    private String username;
    private String email;

}
