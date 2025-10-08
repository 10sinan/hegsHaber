
package com.sinan.hegsHaber.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private UserDto user;
    private String message;
    private String token;
    private Integer userCoins;

}