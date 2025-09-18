package com.sinan.hegsHaber.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<NewsDto> getGuncel() {
        return newsService.haberiCek("https://www.aa.com.tr/tr/rss/default?cat=guncel", 10);// haberleri cek
    }

    @GetMapping("/dunya")
    public List<NewsDto> getDunya() {
        return newsService.haberiCek("https://www.aa.com.tr/tr/rss/default?cat=dunya", 10);// haberleri cek
    }

    @GetMapping("/ekonomi")
    public List<NewsDto> getEkonomi() {
        return newsService.haberiCek("https://www.aa.com.tr/tr/rss/default?cat=ekonomi", 10);// haberleri cek
    }

}
