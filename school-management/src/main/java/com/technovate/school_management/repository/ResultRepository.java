package com.technovate.school_management.repository;

import com.technovate.school_management.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByStudentId(Long studentId);
    List<Result> findByStudentIdNumber(String studentIdNumber);
}
