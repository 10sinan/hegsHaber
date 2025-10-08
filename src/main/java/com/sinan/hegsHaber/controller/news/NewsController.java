package com.sinan.hegsHaber.controller.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinan.hegsHaber.dto.NewsDto;
import com.sinan.hegsHaber.service.NewsService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/news")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/guncel")
    public ResponseEntity<List<NewsDto>> getGuncel() {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.haberiCek("https://www.aa.com.tr/tr/rss/default?cat=guncel", 10));// haberleri cek
    }

    @GetMapping("/dunya")
    public ResponseEntity<List<NewsDto>> getDunya() {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.haberiCek("https://www.aa.com.tr/tr/rss/default?cat=dunya", 10));// haberleri cek
    }

    @GetMapping("/ekonomi")
    public ResponseEntity<List<NewsDto>> getEkonomi() {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.haberiCek("https://www.aa.com.tr/tr/rss/default?cat=ekonomi", 10));// haberleri cek
    }

    @GetMapping("/spor")
    public ResponseEntity<List<NewsDto>> getSpor() {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.haberiCek("https://www.aa.com.tr/tr/rss/default?cat=spor", 10));// haberleri cek
    }

    @GetMapping("/saglik")
    public ResponseEntity<List<NewsDto>> getSaglik() {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.haberiCek("https://www.aa.com.tr/tr/rss/default?cat=saglik", 10));// haberleri cek
    }

    @GetMapping("/teknoloji")
    public ResponseEntity<List<NewsDto>> getTeknoloji() {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.haberiCek("https://www.aa.com.tr/tr/rss/default?cat=teknoloji", 10));// haberleri cek
    }

}
