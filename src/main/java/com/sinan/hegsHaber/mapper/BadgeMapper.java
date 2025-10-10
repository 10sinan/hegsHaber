package com.sinan.hegsHaber.mapper;

import com.sinan.hegsHaber.dto.auth.UserDto;
import com.sinan.hegsHaber.dto.social.BadgeDto;
import com.sinan.hegsHaber.entity.social.Badge;
import com.sinan.hegsHaber.entity.user.User;

public class BadgeMapper {
    public static BadgeDto toBadgeDto(Badge badge) {
        if (badge == null)
            return null;
        BadgeDto dto = new BadgeDto();
        dto.setId(badge.getId());
        dto.setName(badge.getName());
        dto.setDescription(badge.getDescription());
        dto.setUser(toUserDto(badge.getUser()));
        return dto;
    }

    public static UserDto toUserDto(User user) {
        if (user == null)
            return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
