package com.technovate.school_management.repository;

import com.technovate.school_management.entity.StudentClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
    Optional<StudentClass> findFirstByStudentIdAndIsCurrent(Long studentId, boolean isCurrent);
    Page<StudentClass> findAllByIsCurrent(boolean isCurrent, Pageable pageable);
    List<StudentClass> findAllBySchoolClassId(Long schoolClasId);
}
