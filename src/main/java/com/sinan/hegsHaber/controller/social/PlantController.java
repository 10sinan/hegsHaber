package com.sinan.hegsHaber.controller.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sinan.hegsHaber.entity.social.Plant_types;
import com.sinan.hegsHaber.service.social.PlantService;

@RestController
@RequestMapping("/plants")

public class PlantController {

    @Autowired
    private PlantService plantService;

    @GetMapping("/{id}") // bitki türünü id'ye göre döner
    public ResponseEntity<Plant_types> getPlantById(@PathVariable Long id) {
        Plant_types plant = plantService.getPlantById(id);
        if (plant != null) {
            return ResponseEntity.status(HttpStatus.OK).body(plant);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/addPlant") // bitki türü ekleme
    public ResponseEntity<Plant_types> addPlant(@RequestBody Plant_types plant) {
        Plant_types createdPlant = plantService.addPlant(plant);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlant);
    }
}
