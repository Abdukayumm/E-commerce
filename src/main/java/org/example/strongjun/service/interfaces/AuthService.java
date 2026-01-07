package org.example.strongjun.service.interfaces;


import org.example.strongjun.DTOs.UserDTO;
import org.example.strongjun.DTOs.req.LoginRequestDTO;
import org.example.strongjun.DTOs.req.RegisterRequestDTO;
import org.example.strongjun.DTOs.res.AuthResponseDTO;

public interface AuthService {
    UserDTO register(RegisterRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
}
