package com.sinan.hegsHaber.mapper;

import com.sinan.hegsHaber.dto.auth.RegisterRequestDTO;
import com.sinan.hegsHaber.dto.auth.UserDto;
import com.sinan.hegsHaber.entity.user.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // MapStruct kullanarak DTO ve Entity dönüşümleri için arayüz
    UserDto toUserDTO(User user);


    @Mapping(target = "receivedFriendRequests", ignore = true)
    @Mapping(target = "security", ignore = true)
    @Mapping(target = "sentFriendRequests", ignore = true)
    
    User toUser(UserDto userDTO);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "receivedFriendRequests", ignore = true)
    @Mapping(target = "security", ignore = true)
    @Mapping(target = "sentFriendRequests", ignore = true)
    
    User toUser(RegisterRequestDTO registerRequestDTO);
}
