package org.example.strongjun.DTOs.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String email;
    private String token;
}
