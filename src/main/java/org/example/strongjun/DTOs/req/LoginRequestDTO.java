package org.example.strongjun.DTOs.req;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
