package com.technovate.school_management.service;

import com.technovate.school_management.entity.Role;
import com.technovate.school_management.entity.User;
import com.technovate.school_management.entity.enums.UserRoles;
import com.technovate.school_management.repository.RoleRepository;
import com.technovate.school_management.repository.UserRepository;
import com.technovate.school_management.service.contracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUserWithRoles(String username, String password, List<UserRoles> roles) {
        Optional<User> existingUserOpt = userRepository.findByUsername(username);
        if (existingUserOpt.isPresent()) {
            throw new IllegalArgumentException("A user with the username " + username + " already exists");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword);
        List<Role> userRoles = new ArrayList<>();
        for (UserRoles role : roles) {
            Optional<Role> existingRole = roleRepository.findByRole(role);
            existingRole.ifPresent(userRoles::add);
        }
        user.setRoles(userRoles);
        userRepository.save(user);
        return user;
    }
}
