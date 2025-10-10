package com.sinan.hegsHaber.dto.social;

import com.sinan.hegsHaber.dto.auth.UserDto;

import lombok.Data;

@Data
public class BadgeDto {
    private Long id;
    private String name;
    private String description;
    private UserDto user;
}
