package com.sinan.hegsHaber.mapper;

import com.sinan.hegsHaber.dto.FriendshipDto;
import com.sinan.hegsHaber.entity.Friendship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {
    @Mapping(target = "username", source = "following.username")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToString")
    FriendshipDto toDto(Friendship friendship);

    @Named("instantToString")
    public static String instantToString(java.time.Instant instant) {
        return instant == null ? null : instant.toString();
    }
}
