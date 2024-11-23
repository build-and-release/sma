package com.technovate.school_management.seedDB;

import com.technovate.school_management.entity.Role;
import com.technovate.school_management.entity.enums.UserRoles;
import com.technovate.school_management.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if the roles table is empty
        if (roleRepository.count() == 0) {
            // Seed all roles
            Arrays.stream(UserRoles.values()).forEach(roleEnum -> {
                Role role = new Role(roleEnum);
                roleRepository.save(role);
            });

            System.out.println("Roles seeded.");
        } else {
            System.out.println("Roles already exist.");
        }
    }
}