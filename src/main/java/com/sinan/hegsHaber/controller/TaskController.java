package com.sinan.hegsHaber.controller;

import com.sinan.hegsHaber.entity.Task;
import com.sinan.hegsHaber.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/daily-tasks") // Her gün yapılacak görevleri listele
    public ResponseEntity<List<Task>> dailyTasks() {// Tüm görevleri listele
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getdailyTasks());
    }

    @GetMapping("/{id}") // ID'ye göre görev getir @PathVariable ile
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(task.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/createTask")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

}
