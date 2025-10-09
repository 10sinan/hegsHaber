package com.sinan.hegsHaber.dto.social;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PetDto {

    private Long id;// evcil hayvan id
    private boolean isActive;// evcil hayvan seçili mi
    private LocalDateTime createdAt;// evcil hayvan sahiplenme tarihi

}
