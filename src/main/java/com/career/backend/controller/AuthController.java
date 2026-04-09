package com.career.backend.controller;

import com.career.backend.dto.AuthDtos.LoginRequest;
import com.career.backend.dto.AuthDtos.SignupRequest;
import com.career.backend.model.RoleType;
import com.career.backend.model.User;
import com.career.backend.repository.RoleRepository;
import com.career.backend.repository.UserRepository;
import com.career.backend.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest req) {
        if (!req.getPassword().equals(req.getConfirmPassword())) return ResponseEntity.badRequest().body("Passwords do not match");
        RoleType role = RoleType.valueOf(req.getRole().toUpperCase());
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(roleRepository.findByName(role).orElseThrow())
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "Signup successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow();
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) return ResponseEntity.badRequest().body("Invalid credentials");
        String token = jwtService.generateToken(user.getEmail(), user.getRole().getName().name());
        return ResponseEntity.ok(Map.of(
                "accessToken", token,
                "user", Map.of("id", user.getId(), "name", user.getName(), "email", user.getEmail(), "role", user.getRole().getName().name())
        ));
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh() {
        return ResponseEntity.ok(Map.of("message", "Implement refresh with token rotation"));
    }
}

