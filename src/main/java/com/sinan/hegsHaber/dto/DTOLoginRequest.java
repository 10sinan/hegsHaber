package com.sinan.hegsHaber.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DTOLoginRequest {
    @NotBlank // Kullanicı adi bos olamaz
    private String username;

    @NotBlank // Sifre bos olamaz
    private String password;
}