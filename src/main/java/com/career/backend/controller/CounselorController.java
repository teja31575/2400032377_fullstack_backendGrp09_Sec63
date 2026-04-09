package com.career.backend.controller;

import com.career.backend.model.ChatMessage;
import com.career.backend.model.CounselingSession;
import com.career.backend.model.User;
import com.career.backend.repository.ChatMessageRepository;
import com.career.backend.repository.CounselingSessionRepository;
import com.career.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/counselor")
@RequiredArgsConstructor
public class CounselorController {
    private final CounselingSessionRepository sessionRepository;
    private final ChatMessageRepository chatRepository;
    private final UserRepository userRepository;

    @GetMapping("/sessions")
    public ResponseEntity<?> sessions(@RequestParam Long counselorId) { return ResponseEntity.ok(sessionRepository.findByCounselorId(counselorId)); }

    @PostMapping("/approve-session")
    public ResponseEntity<?> approve(@RequestBody Map<String, String> req) {
        CounselingSession s = sessionRepository.findById(Long.valueOf(req.get("sessionId"))).orElseThrow();
        s.setStatus(req.getOrDefault("status", "APPROVED"));
        return ResponseEntity.ok(sessionRepository.save(s));
    }

    @PostMapping("/chat/{studentId}")
    public ResponseEntity<?> sendChat(@PathVariable Long studentId, @RequestBody Map<String, String> req) {
        User counselor = userRepository.findById(Long.valueOf(req.get("counselorId"))).orElseThrow();
        User student = userRepository.findById(studentId).orElseThrow();
        ChatMessage msg = ChatMessage.builder().sender(counselor).receiver(student).message(req.get("message")).sentAt(LocalDateTime.now()).build();
        return ResponseEntity.ok(chatRepository.save(msg));
    }
}

