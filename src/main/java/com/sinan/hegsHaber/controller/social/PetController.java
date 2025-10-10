package com.sinan.hegsHaber.controller.social;

import com.sinan.hegsHaber.entity.social.Pet_types;
import com.sinan.hegsHaber.service.social.PetService;
import com.sinan.hegsHaber.dto.social.PetDto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pets")
public class PetController {
    @Autowired
    private PetService petService;

    @PostMapping("/add") // yeni pet ekleme
    public ResponseEntity<PetDto> addPet(@RequestBody Pet_types pet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.addPet(pet).getBody());
    }

    @GetMapping("/all-pets") // tum petleri listeleme
    public ResponseEntity<List<Pet_types>> getAllPets() {
        return ResponseEntity.status(HttpStatus.OK).body(petService.getAllPets().getBody());
    }
}
