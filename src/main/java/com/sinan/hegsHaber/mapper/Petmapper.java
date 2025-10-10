package com.sinan.hegsHaber.mapper;

import org.mapstruct.Mapper;

import com.sinan.hegsHaber.dto.social.PetDto;
import com.sinan.hegsHaber.entity.user.UserPets;

@Mapper(componentModel = "spring")
public class Petmapper {
    public PetDto convertToDto(UserPets userPet) {// UserPets entity'sini PetDto'ya donustur
        PetDto petDto = new PetDto();// PetDto nesnesi olustur
        petDto.setId(userPet.getPetId());// evcil hayvan id'sini ayarla
        petDto.setActive(userPet.getIsActive());// evcil hayvanin aktif olup olmadigini ayarla
        return petDto;
    }

}
