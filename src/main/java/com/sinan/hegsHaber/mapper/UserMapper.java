package com.sinan.hegsHaber.mapper;

import com.sinan.hegsHaber.dto.RegisterRequestDTO;
import com.sinan.hegsHaber.dto.UserDto;import com.sinan.hegsHaber.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper { 
    // MapStruct kullanarak DTO ve Entity dönüşümleri için arayüz
    UserDto toUserDTO(User user);

    User toUser(UserDto userDTO);

    User toUser(RegisterRequestDTO registerRequestDTO);
}
