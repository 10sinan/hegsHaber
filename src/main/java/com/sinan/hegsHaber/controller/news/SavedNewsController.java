package com.sinan.hegsHaber.controller.news;

import com.sinan.hegsHaber.entity.social.SavedNews;
import com.sinan.hegsHaber.service.social.SavedNewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/saved-news")
public class SavedNewsController {
    @Autowired
    private SavedNewsService savedNewsService;

    @PostMapping("/save")
    public ResponseEntity<SavedNews> saveNews(@RequestBody SavedNews savedNews) {
        SavedNews saved = savedNewsService.save(savedNews);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);//
    }

    @GetMapping("/all")
    public ResponseEntity<List<SavedNews>> getAllSavedNews() {
        List<SavedNews> all = savedNewsService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }
}
