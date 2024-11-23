package com.technovate.school_management.repository;

import com.technovate.school_management.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {
    Optional<Guardian> findByEmailOrPhoneNumber(String email, String phoneNumber);
    Optional<Guardian> findByEmail(String email);
}
