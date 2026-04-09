package com.career.backend.config;

import com.career.backend.model.Role;
import com.career.backend.model.RoleType;
import com.career.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner initRoles() {
        return args -> {
            for (RoleType type : RoleType.values()) {
                roleRepository.findByName(type).orElseGet(() -> roleRepository.save(Role.builder().name(type).build()));
            }
        };
    }
}

