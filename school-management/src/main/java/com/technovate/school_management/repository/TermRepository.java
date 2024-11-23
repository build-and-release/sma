package com.technovate.school_management.repository;

import com.technovate.school_management.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TermRepository extends JpaRepository<Term, Long> {
    Optional<Term> findByIsCurrentTrue();
}
