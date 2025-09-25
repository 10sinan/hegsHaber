package com.sinan.hegsHaber.mapper;

import com.sinan.hegsHaber.dto.RegisterRequestDTO;
import com.sinan.hegsHaber.dto.UserDTO;
import com.sinan.hegsHaber.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper { 
    // MapStruct kullanarak DTO ve Entity dönüşümleri için arayüz
    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);

    User toUser(RegisterRequestDTO registerRequestDTO);
}
