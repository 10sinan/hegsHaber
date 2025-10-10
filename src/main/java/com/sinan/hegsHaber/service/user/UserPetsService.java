package com.sinan.hegsHaber.service.user;

import com.sinan.hegsHaber.dto.social.PetDto;
import com.sinan.hegsHaber.entity.user.UserPets;
import com.sinan.hegsHaber.mapper.Petmapper;
import com.sinan.hegsHaber.repository.user.UserPetsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserPetsService {
    public void assignPetToUser(UUID userId, Long petId) {
        UserPets userPet = new UserPets();
        userPet.setUserUuid(userId);
        userPet.setPetId(petId);
        userPet.setIsActive(true); // Varsayılan olarak aktif
        userPetsRepository.save(userPet);
    }

    @Autowired
    private UserPetsRepository userPetsRepository;
    private Petmapper petmapper;

    public List<PetDto> getUserPets(UUID userUuid) {// belirli bir kullanicinin petlerini getir
        List<UserPets> userPets = userPetsRepository.findByUserUuid(userUuid);
        return userPets.stream().map(petmapper::convertToDto).toList();// UserPets entity listesini PetDto listesine
                                                                       // donustur
    }

    public UserPets getActiveUserPet(UUID userUuid) {// belkı lazım olur
        List<UserPets> pets = userPetsRepository.findByUserUuidAndIsActive(userUuid, true);
        return pets.isEmpty() ? null : pets.get(0);
    }

    public UserPets addUserPet(UUID userUuid, UserPets userPet) {
        userPet.setUserUuid(userUuid);
        return userPetsRepository.save(userPet);
    }

    public void deleteUserPet(Long id) {// belkı lazım olur
        userPetsRepository.deleteById(id);
    }

    public void setActivePet(UUID userUuid, Long petId) {// belkı lazım olur
        List<UserPets> pets = userPetsRepository.findByUserUuid(userUuid);
        for (UserPets pet : pets) {
            pet.setIsActive(pet.getPetId().equals(petId));
            userPetsRepository.save(pet);
        }
    }
}
