package com.sinan.hegsHaber.service;

import com.sinan.hegsHaber.entity.social.Task;
import com.sinan.hegsHaber.repository.social.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getdailyTasks() {
        return taskRepository.findAll().stream().limit(3).toList();// İlk 3 görevi döndür
    }

    public Optional<Task> getTaskById(Long id) { // ID'ye göre görevi bul
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

}
