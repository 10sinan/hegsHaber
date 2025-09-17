
package com.sinan.hegsHaber.dto;

import lombok.Data;

@Data
public class DTOAuthResponse {
    private String message; // Islem sonucu mesaji
    private String token; // JWT eklersek buraya gelir
}