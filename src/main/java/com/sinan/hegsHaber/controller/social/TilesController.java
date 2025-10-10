package com.sinan.hegsHaber.controller.social;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinan.hegsHaber.entity.social.Tiles;
import com.sinan.hegsHaber.service.social.TilesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/tiles")
public class TilesController {

    @Autowired
    TilesService tilesService;

    @GetMapping("/{id}")
    public ResponseEntity<Tiles> getById(@PathVariable Long id) {// id ile tile getir
        return ResponseEntity.status(HttpStatus.OK).body(tilesService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tiles>> getAllTiles() {// tüm tileları getir
        return ResponseEntity.status(HttpStatus.OK).body(tilesService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Tiles> createTiles(@RequestBody Tiles tiles) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tilesService.create(tiles));
    }

}
