
package com.sinan.hegsHaber.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {// Kimlik dogrulama islemlerinin cevabi
    private String message; // Islem sonucu mesaji
    private String token; // JWT eklersek buraya gelir
}