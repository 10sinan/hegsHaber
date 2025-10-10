package com.sinan.hegsHaber.service.social;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinan.hegsHaber.entity.social.Tiles;
import com.sinan.hegsHaber.repository.social.TilesRepository;

@Service
public class TilesService {

    @Autowired
    TilesRepository tilesRepository;

    public Tiles getById(Long id) {
        return tilesRepository.findById(id).orElse(null);// id ye gore
    }

    public Tiles create(Tiles tiles) {
        return tilesRepository.save(tiles);
    }

    public List<Tiles> getAll() {
        return tilesRepository.findAll();
    }

}
