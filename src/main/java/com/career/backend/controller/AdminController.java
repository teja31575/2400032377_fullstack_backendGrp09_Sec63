package com.career.backend.controller;

import com.career.backend.model.CareerPath;
import com.career.backend.model.CareerResource;
import com.career.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
    private final CareerResourceRepository resourceRepository;
    private final CareerPathRepository pathRepository;

    @GetMapping("/users")
    public ResponseEntity<?> users() { return ResponseEntity.ok(userRepository.findAll()); }

    @PostMapping("/career-resources")
    public ResponseEntity<?> createResource(@RequestBody CareerResource resource) { return ResponseEntity.ok(resourceRepository.save(resource)); }
    @PutMapping("/career-resources/{id}")
    public ResponseEntity<?> updateResource(@PathVariable Long id, @RequestBody CareerResource payload) {
        CareerResource r = resourceRepository.findById(id).orElseThrow();
        r.setTitle(payload.getTitle()); r.setUrl(payload.getUrl()); r.setContent(payload.getContent());
        return ResponseEntity.ok(resourceRepository.save(r));
    }
    @DeleteMapping("/career-resources/{id}")
    public ResponseEntity<?> deleteResource(@PathVariable Long id) { resourceRepository.deleteById(id); return ResponseEntity.ok(Map.of("deleted", id)); }

    @PostMapping("/career-paths")
    public ResponseEntity<?> createPath(@RequestBody CareerPath path) { return ResponseEntity.ok(pathRepository.save(path)); }
    @PutMapping("/career-paths/{id}")
    public ResponseEntity<?> updatePath(@PathVariable Long id, @RequestBody CareerPath payload) {
        CareerPath p = pathRepository.findById(id).orElseThrow();
        p.setTitle(payload.getTitle()); p.setDescription(payload.getDescription());
        return ResponseEntity.ok(pathRepository.save(p));
    }
    @DeleteMapping("/career-paths/{id}")
    public ResponseEntity<?> deletePath(@PathVariable Long id) { pathRepository.deleteById(id); return ResponseEntity.ok(Map.of("deleted", id)); }

    @PostMapping("/assign-counselor")
    public ResponseEntity<?> assignCounselor() { return ResponseEntity.ok(Map.of("message", "Counselor assigned")); }

    @GetMapping("/analytics")
    public ResponseEntity<?> analytics() {
        return ResponseEntity.ok(Map.of(
                "totalUsers", userRepository.count(),
                "totalCareerResources", resourceRepository.count(),
                "totalCareerPaths", pathRepository.count()
        ));
    }
}

