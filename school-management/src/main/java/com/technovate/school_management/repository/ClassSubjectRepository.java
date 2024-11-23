package com.technovate.school_management.repository;

import com.technovate.school_management.entity.ClassSubject;
import com.technovate.school_management.entity.enums.ClassEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassSubjectRepository extends JpaRepository<ClassSubject, Long> {
    Optional<ClassSubject> findBySchoolClassIdAndSubjectId(Long schoolClassId, Long subjectId);
    List<ClassSubject> findBySchoolClassName(ClassEnum schoolClassName);

}
