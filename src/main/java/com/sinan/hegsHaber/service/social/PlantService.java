package com.sinan.hegsHaber.service.social;

import com.sinan.hegsHaber.entity.social.Plant_types;
import com.sinan.hegsHaber.repository.social.PlantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantService {
    @Autowired
    private PlantRepository plantRepository;

    public Plant_types getPlantById(Long id) {// bitki türünü id'ye göre getir
        return plantRepository.findById(id).orElse(null);
    }

    public Plant_types addPlant(Plant_types plant) {// bitki türü ekle
        return plantRepository.save(plant);
    }
}
