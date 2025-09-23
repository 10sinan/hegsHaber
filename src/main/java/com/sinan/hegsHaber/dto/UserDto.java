package com.sinan.hegsHaber.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String role;
    private List<Long> friends;
    private int totalXp;
}
