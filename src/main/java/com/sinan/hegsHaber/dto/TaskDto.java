package com.sinan.hegsHaber.dto;

import lombok.Data;

// id artık Long tipinde olacak

@Data
public class TaskDto {
    private Long id;
    private String description;

}
