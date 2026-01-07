package org.example.strongjun.contollers;

import org.example.strongjun.DTOs.UserDTO;
import org.example.strongjun.DTOs.req.LoginRequestDTO;
import org.example.strongjun.DTOs.req.RegisterRequestDTO;
import org.example.strongjun.DTOs.res.AuthResponseDTO;
import org.example.strongjun.service.interfaces.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }
}
