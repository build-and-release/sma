package com.technovate.school_management.repository;

import com.technovate.school_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByIdNumber(String idNumber);
    Optional<Student> findByUserId(Long userId);
}
