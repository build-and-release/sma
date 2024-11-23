package com.technovate.school_management.repository;

import com.technovate.school_management.entity.SchoolFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolFeeRepository extends JpaRepository<SchoolFee, Long> {
    Optional<SchoolFee> findBySchoolClassIdAndTermId(Long schoolClassId, Long termId);
}
