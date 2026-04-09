package com.career.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "counseling_sessions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CounselingSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "student_id")
    private User student;
    @ManyToOne @JoinColumn(name = "counselor_id")
    private User counselor;
    private LocalDateTime sessionTime;
    private String status;
}

