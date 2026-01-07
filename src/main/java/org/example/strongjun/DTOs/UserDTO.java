package org.example.strongjun.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.strongjun.entity.enums.Role;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private Role role;
}
