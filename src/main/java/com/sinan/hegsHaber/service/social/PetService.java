package com.sinan.hegsHaber.service.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sinan.hegsHaber.entity.social.Pet_types;
import com.sinan.hegsHaber.repository.social.PetRepository;
import com.sinan.hegsHaber.dto.social.PetDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    public ResponseEntity<List<PetDto>> getAllPets() {
        List<Pet_types> pets = petRepository.findAll();
        List<PetDto> petDtos = pets.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(petDtos);
    }

    public ResponseEntity<PetDto> addPet(@RequestBody Pet_types pet) {
        Pet_types saved = petRepository.save(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    private PetDto toDto(Pet_types pet) {
        PetDto dto = new PetDto();
        dto.setId(pet.getId());
        dto.setActive(true); // Pet_types için aktiflik varsayılan true
        dto.setCreatedAt(pet.getCreatedAt() != null
                ? pet.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
                : null);
        return dto;
    }
}
