package com.sinan.hegsHaber.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank // Kullanicı adi bos olamaz
    private String username;

    @NotBlank // Sifre bos olamaz
    private String password;

    @NotBlank // E-posta bos olamaz
    @Email(message = "Geçerli bir email adresi giriniz")
    private String email;
}