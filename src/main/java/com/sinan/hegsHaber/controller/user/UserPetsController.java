package com.sinan.hegsHaber.controller.user;

import com.sinan.hegsHaber.entity.user.UserPets;
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
    public ResponseEntity<List<UserPets>> getPetsByUser(@PathVariable("userUuid") String userUuid) {
        UUID uuid = UUID.fromString(userUuid);// String'i UUID'ye donustur
        return ResponseEntity.status(HttpStatus.OK).body(userPetsService.getUserPets(uuid));
    }

    @PostMapping("/add") // kullaniciya yeni pet ekleme
    public ResponseEntity<UserPets> addUserPet(@RequestBody UserPets userPet) {
        UserPets createdPet = userPetsService.addUserPet(userPet);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }

}
