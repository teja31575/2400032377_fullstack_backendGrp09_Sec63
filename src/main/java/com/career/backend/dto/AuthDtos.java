package com.career.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class AuthDtos {
    @Data
    public static class SignupRequest {
        @NotBlank private String name;
        @Email private String email;
        @NotBlank private String password;
        @NotBlank private String confirmPassword;
        @NotBlank private String role;
    }
    @Data
    public static class LoginRequest {
        @Email private String email;
        @NotBlank private String password;
    }
}

