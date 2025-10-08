package com.sinan.hegsHaber.repository.social;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinan.hegsHaber.entity.social.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
