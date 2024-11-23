package com.technovate.school_management.repository;

import com.technovate.school_management.entity.Subject;
import com.technovate.school_management.entity.enums.ClassLevelEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByTitleAndLevel(String title, ClassLevelEnum level);
    List<Subject> findAllByLevel(ClassLevelEnum level);
}
