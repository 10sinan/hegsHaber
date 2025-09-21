package com.sinan.hegsHaber.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
// Kullanicı kayit istegi icin DTO
public class DTORegisterRequest {
    @NotBlank // Kullanicı adi bos olamaz
    private String username; // Kullanicı adi

    @NotBlank // email bos olamaz
    @Email(message = "Geçerli bir email adresi giriniz")
    private String email; // Email

    @NotBlank // Sifre bos olamaz
    private String password; // Sifre

}
