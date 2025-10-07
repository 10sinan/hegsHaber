package com.sinan.hegsHaber.service;

import com.sinan.hegsHaber.entity.SavedNews;
import com.sinan.hegsHaber.repository.SavedNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SavedNewsService {
    @Autowired
    private SavedNewsRepository savedNewsRepository;

    public SavedNews save(SavedNews savedNews) {
        return savedNewsRepository.save(savedNews);
    }

    public List<SavedNews> getAll() {
        return savedNewsRepository.findAll();
    }
}
