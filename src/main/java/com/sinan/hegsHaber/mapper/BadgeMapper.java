package com.sinan.hegsHaber.mapper;

import com.sinan.hegsHaber.dto.social.BadgeDto;
import com.sinan.hegsHaber.entity.social.Badge;

public class BadgeMapper {
    public static BadgeDto toBadgeDto(Badge badge) {
        if (badge == null)
            return null;
        BadgeDto dto = new BadgeDto();
        dto.setId(badge.getId());
        dto.setName(badge.getName());
        dto.setDescription(badge.getDescription());
        return dto;
    }

}
