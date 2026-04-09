package com.career.backend.repository;

import com.career.backend.model.CounselingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CounselingSessionRepository extends JpaRepository<CounselingSession, Long> {
    List<CounselingSession> findByCounselorId(Long counselorId);
}

