package com.career.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "counselors")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CounselorProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String specialization;
    private Integer experienceYears;
}

