package com.career.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "career_resources")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CareerResource {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String url;
    @Column(length = 1000)
    private String content;
}

