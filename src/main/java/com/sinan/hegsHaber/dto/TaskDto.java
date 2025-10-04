package com.sinan.hegsHaber.dto;

// id artık Long tipinde olacak

public class TaskDto {
    private Long id;
    private String title;
    private String description;
    // Gerekirse diğer alanlar eklenebilir

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
