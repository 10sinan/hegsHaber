package com.sinan.hegsHaber.service.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestBody;

import com.sinan.hegsHaber.entity.social.Pet_types;
import com.sinan.hegsHaber.repository.social.PetRepository;

import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    public ResponseEntity<List<Pet_types>> getAllPets() {
        return ResponseEntity.status(HttpStatus.OK).body(petRepository.findAll());
    }

    public ResponseEntity<Pet_types> addPet(@RequestBody Pet_types pet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petRepository.save(pet));
    }
}
