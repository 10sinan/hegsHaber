package com.sinan.hegsHaber.mapper;

import com.sinan.hegsHaber.dto.RegisterRequestDTO;
import com.sinan.hegsHaber.dto.UserDto;
import com.sinan.hegsHaber.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {// MapStruct kullanarak DTO ve Entity dönüşümleri için arayüz

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "friends", ignore = true)
    @Mapping(target = "totalXp", ignore = true)
    public User dtoToUser(RegisterRequestDTO dto);

    // User -> UserDto
    @Mapping(target = "friends", ignore = true)
    @Mapping(target = "totalXp", ignore = true)
    UserDto userToUserDto(User user);
}
