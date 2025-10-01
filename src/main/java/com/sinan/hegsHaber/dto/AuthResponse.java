
package com.sinan.hegsHaber.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private UserDto user;
    private String message;
}