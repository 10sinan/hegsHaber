package com.sinan.hegsHaber.controller.user;

import com.sinan.hegsHaber.dto.social.PetDto;

import com.sinan.hegsHaber.service.user.UserPetsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-pets")
public class UserPetsController {

    @Autowired
    private UserPetsService userPetsService;

    @GetMapping("/by-user/{userUuid}") // belirli bir kullanicinin petlerini listeleme
    public ResponseEntity<List<PetDto>> getPetsByUser(@PathVariable UUID userUuid) {
        List<PetDto> pets = userPetsService.getUserPets(userUuid);
        return ResponseEntity.status(HttpStatus.OK).body(pets);
    }

    @PostMapping("/assign") // kullaniciya pet atama
    public ResponseEntity<String> assignPetToUser(@RequestParam UUID userId, @RequestParam Long petId) {
        userPetsService.assignPetToUser(userId, petId);
        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }

}
