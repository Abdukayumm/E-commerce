package org.example.strongjun.DTOs.req;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String email;
    private String password;
}
