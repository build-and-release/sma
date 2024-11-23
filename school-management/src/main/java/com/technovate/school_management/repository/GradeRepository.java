package com.technovate.school_management.repository;

import com.technovate.school_management.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findByResultIdAndClassSubjectId(Long resultId, Long classSubjectId);
    List<Grade> findByResultId(Long resultId);
}
