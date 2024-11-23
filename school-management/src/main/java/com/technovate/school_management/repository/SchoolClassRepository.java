package com.technovate.school_management.repository;

import com.technovate.school_management.entity.SchoolClass;
import com.technovate.school_management.entity.enums.ClassEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    Optional<SchoolClass> findByNextClassId(Long nextClassId);
    Optional<SchoolClass> findByName(ClassEnum name);
}
