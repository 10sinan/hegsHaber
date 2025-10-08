package com.sinan.hegsHaber.service.user;

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

    public List<UserPets> getUserPets(UUID userUuid) {
        return userPetsRepository.findByUserUuid(userUuid);
    }
    

    public UserPets getActiveUserPet(UUID userUuid) {
        List<UserPets> pets = userPetsRepository.findByUserUuidAndIsActive(userUuid, true);
        return pets.isEmpty() ? null : pets.get(0);
    }

    public UserPets addUserPet(UserPets userPet) {
        return userPetsRepository.save(userPet);
    }

    public void deleteUserPet(Long id) {
        userPetsRepository.deleteById(id);
    }

    public void setActivePet(UUID userUuid, Long petId) {
        List<UserPets> pets = userPetsRepository.findByUserUuid(userUuid);
        for (UserPets pet : pets) {
            pet.setIsActive(pet.getPetId().equals(petId));
            userPetsRepository.save(pet);
        }
    }
}
