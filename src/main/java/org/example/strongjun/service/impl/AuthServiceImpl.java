package org.example.strongjun.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.strongjun.DTOs.UserDTO;
import org.example.strongjun.DTOs.req.LoginRequestDTO;
import org.example.strongjun.DTOs.req.RegisterRequestDTO;
import org.example.strongjun.DTOs.res.AuthResponseDTO;
import org.example.strongjun.config.JwtUtil;
import org.example.strongjun.entity.User;
import org.example.strongjun.entity.enums.Role;
import org.example.strongjun.repo.UserRepository;
import org.example.strongjun.service.interfaces.AuthService;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public UserDTO register(RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return new UserDTO(user.getId(), user.getEmail(), user.getRole());
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            String token = jwtUtil.generateToken(request.getEmail());
            return new AuthResponseDTO(request.getEmail(), token);

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid email or password");
        }
    }
}
