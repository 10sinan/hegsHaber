package com.sinan.hegsHaber.repository;

import com.sinan.hegsHaber.entity.SavedNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedNewsRepository extends JpaRepository<SavedNews, Long> {
}
