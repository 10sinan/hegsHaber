package com.sinan.hegsHaber.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
// Kullanicı kayit istegi icin DTO
public class DTORegisterRequest {
    @NotBlank // Kullanicı adi bos olamaz
    private String username; // Kullanicı adi

    @NotBlank // Sifre bos olamaz
    private String password; // Sifre
}
