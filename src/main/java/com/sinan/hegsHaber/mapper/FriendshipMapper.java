package com.sinan.hegsHaber.mapper;

import com.sinan.hegsHaber.dto.social.FriendshipDto;
import com.sinan.hegsHaber.entity.social.Friendship;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {
    @Mapping(target = "id", source = "following.id")
    @Mapping(target = "username", source = "following.username")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToString")
    FriendshipDto toDto(Friendship friendship);

    default FriendshipDto toDto(Friendship friendship, java.util.UUID userId) {
        FriendshipDto dto = new FriendshipDto();
        if (friendship.getFollower().getId().equals(userId)) {
            dto.setId(friendship.getFollowing().getId().toString());
            dto.setUsername(friendship.getFollowing().getUsername());
        } else {
            dto.setId(friendship.getFollower().getId().toString());
            dto.setUsername(friendship.getFollower().getUsername());
        }
        dto.setCreatedAt(instantToString(friendship.getCreatedAt()));
        return dto;
    }

    @Named("instantToString")
    public static String instantToString(java.time.Instant instant) {
        return instant == null ? null : instant.toString();
    }

}
