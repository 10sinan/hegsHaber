package com.sinan.hegsHaber.mapper;

import com.sinan.hegsHaber.dto.FriendshipDto;
import com.sinan.hegsHaber.entity.Friendship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {
    @Mapping(target = "requesterUsername", source = "requester.username")
    @Mapping(target = "receiverUsername", source = "receiver.username")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToString")
    @Mapping(target = "respondedAt", source = "respondedAt", qualifiedByName = "instantToString")
    FriendshipDto toDto(Friendship friendship);

    @Named("instantToString")
    public static String instantToString(java.time.Instant instant) {
        return instant == null ? null : instant.toString();
    }
}
