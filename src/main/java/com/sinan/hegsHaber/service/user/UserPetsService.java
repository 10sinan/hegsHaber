package com.sinan.hegsHaber.service.user;

import com.sinan.hegsHaber.dto.social.PetDto;
import com.sinan.hegsHaber.entity.user.UserPets;
import com.sinan.hegsHaber.repository.user.UserPetsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserPetsService {
    @Autowired
    private UserPetsRepository userPetsRepository;

    public List<PetDto> getUserPets(UUID userUuid) {
        List<UserPets> userPets = userPetsRepository.findByUserUuid(userUuid);
        return userPets.stream().map(this::convertToDto).toList();// UserPets'i PetDto'ya donustur
    }

    private PetDto convertToDto(UserPets userPet) {// UserPets entity'sini PetDto'ya donustur
        PetDto petDto = new PetDto();// PetDto nesnesi olustur
        petDto.setId(userPet.getPetId());// evcil hayvan id'sini ayarla
        petDto.setActive(userPet.getIsActive());// evcil hayvanin aktif olup olmadigini ayarla
        petDto.setCreatedAt(userPet.getCreatedAt());// evcil hayvan sahiplenme tarihini ayarla
        return petDto;
    }

    public UserPets getActiveUserPet(UUID userUuid) {//belkı lazım olur
        List<UserPets> pets = userPetsRepository.findByUserUuidAndIsActive(userUuid, true);
        return pets.isEmpty() ? null : pets.get(0);
    }

    public UserPets addUserPet(UserPets userPet) {
        return userPetsRepository.save(userPet);
    }

    public void deleteUserPet(Long id) {//belkı lazım olur
        userPetsRepository.deleteById(id);
    }

    public void setActivePet(UUID userUuid, Long petId) {//belkı lazım olur
        List<UserPets> pets = userPetsRepository.findByUserUuid(userUuid);
        for (UserPets pet : pets) {
            pet.setIsActive(pet.getPetId().equals(petId));
            userPetsRepository.save(pet);
        }
    }
}
