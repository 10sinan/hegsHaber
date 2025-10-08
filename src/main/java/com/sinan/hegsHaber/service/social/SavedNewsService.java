package com.sinan.hegsHaber.service.social;

import com.sinan.hegsHaber.entity.social.SavedNews;
import com.sinan.hegsHaber.repository.social.SavedNewsRepository;

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
