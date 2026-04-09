package com.career.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "career_paths")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CareerPath {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 1000)
    private String description;
}

