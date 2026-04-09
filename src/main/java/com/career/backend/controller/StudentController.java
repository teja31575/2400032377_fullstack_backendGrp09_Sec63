package com.career.backend.controller;

import com.career.backend.model.*;
import com.career.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    private final CareerPathRepository pathRepository;
    private final CounselingSessionRepository sessionRepository;
    private final BookmarkRepository bookmarkRepository;
    private final CareerResourceRepository resourceRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatRepository;

    @GetMapping("/career-paths")
    public ResponseEntity<?> careerPaths() { return ResponseEntity.ok(pathRepository.findAll()); }

    @PostMapping("/schedule-session")
    public ResponseEntity<?> schedule(@RequestBody Map<String, String> req) {
        User student = userRepository.findById(Long.valueOf(req.get("studentId"))).orElseThrow();
        User counselor = userRepository.findById(Long.valueOf(req.get("counselorId"))).orElseThrow();
        CounselingSession s = CounselingSession.builder().student(student).counselor(counselor).status("PENDING").sessionTime(LocalDateTime.parse(req.get("sessionTime"))).build();
        return ResponseEntity.ok(sessionRepository.save(s));
    }

    @PostMapping("/bookmark")
    public ResponseEntity<?> bookmark(@RequestBody Map<String, Long> req) {
        Bookmark bm = Bookmark.builder()
                .student(userRepository.findById(req.get("studentId")).orElseThrow())
                .resource(resourceRepository.findById(req.get("resourceId")).orElseThrow())
                .build();
        return ResponseEntity.ok(bookmarkRepository.save(bm));
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<?> bookmarks(@RequestParam Long studentId) { return ResponseEntity.ok(bookmarkRepository.findByStudentId(studentId)); }

    @GetMapping("/chat/{counselorId}")
    public ResponseEntity<?> chat(@PathVariable Long counselorId, @RequestParam Long studentId) {
        return ResponseEntity.ok(chatRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(studentId, counselorId, counselorId, studentId));
    }
}

