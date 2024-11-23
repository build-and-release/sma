package com.technovate.school_management.repository;

import com.technovate.school_management.entity.Role;
import com.technovate.school_management.entity.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(UserRoles role);
}