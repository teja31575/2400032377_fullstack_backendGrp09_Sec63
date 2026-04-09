package com.career.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne @JoinColumn(name = "receiver_id")
    private User receiver;
    @Column(length = 2000)
    private String message;
    private LocalDateTime sentAt;
}

