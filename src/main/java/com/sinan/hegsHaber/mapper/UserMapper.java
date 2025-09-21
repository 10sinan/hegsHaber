package com.sinan.hegsHaber.mapper;

import com.sinan.hegsHaber.dto.DTORegisterRequest;
import com.sinan.hegsHaber.dto.AuthResponse;
import com.sinan.hegsHaber.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {// MapStruct kullanarak DTO ve Entity dönüşümleri için arayüz
    
    @Mapping(target = "id", ignore = true) // id veritabanı tarafından oluşturulacak
    @Mapping(target = "role", ignore = true) // rol varsayılan olarak "USER" olacak
    @Mapping(target = "createdAt", ignore = true) // oluşturulma tarihi otomatik olarak ayarlanacak

    public User dtoToUser(DTORegisterRequest dto);// DTO'dan entity'ye dönüşüm(register için)

    @Mapping(target = "message", ignore = true) // mesaj servis tarafından ayarlanacak
    @Mapping(target = "token", ignore = true) // token servis tarafından ayarlanacak

    public AuthResponse userToAuthResponse(User user);// Entity'den DTO'ya dönüşüm(login için)
}
