package com.technovate.school_management.service;

import com.technovate.school_management.dto.CreateUserDto;
import com.technovate.school_management.dto.LoginDto;
import com.technovate.school_management.dto.RoleDto;
import com.technovate.school_management.dto.StudentDto;
import com.technovate.school_management.entity.Role;
import com.technovate.school_management.entity.User;
import com.technovate.school_management.entity.enums.UserRoles;
import com.technovate.school_management.exception.BadRequestException;
import com.technovate.school_management.mapper.RoleMapper;
import com.technovate.school_management.mapper.UserMapper;
import com.technovate.school_management.model.AuthenticationResponse;
import com.technovate.school_management.repository.RoleRepository;
import com.technovate.school_management.repository.UserRepository;
import com.technovate.school_management.service.contracts.AuthService;
import com.technovate.school_management.service.contracts.JwtService;
import com.technovate.school_management.service.contracts.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final StudentService studentService;

    @Value("${application_config.max_password_tries}")
    private int MAX_PASSWORD_TRIES;

    @Override
    public AuthenticationResponse addUser(CreateUserDto createUserDto) {
        User user = userMapper.createUserDtoToUser(createUserDto);
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        List<Role> userRoles = new ArrayList<>();
        Optional<Role> userRole = roleRepository.findByRole(UserRoles.USER);
        userRole.ifPresent(userRoles::add);
        user.setRoles(userRoles);
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public AuthenticationResponse loginUser(LoginDto loginDto) {
        Optional<User> user = userRepository.findByUsername(loginDto.getUsername());
        if (user.isEmpty()) {
            throw new BadRequestException("Invalid username/password");
        }
        User userInfo = user.get();

        if (!userInfo.isAccountNonLocked()) {
            throw new BadRequestException("Account locked due to multiple failed login attempts");
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), userInfo.getPassword())) {
            if (userInfo.getPasswordAttemptCount() < MAX_PASSWORD_TRIES) {
                int updatedPasswordAttemptCount = userInfo.getPasswordAttemptCount() + 1;
                userInfo.setPasswordAttemptCount(updatedPasswordAttemptCount);
                if (updatedPasswordAttemptCount == MAX_PASSWORD_TRIES) {
                    userInfo.setAccountLocked(true);
                    userRepository.save(userInfo);
                    throw new BadRequestException("Account locked due to multiple failed login attempts");
                }
                userRepository.save(userInfo);
                throw new BadRequestException("Invalid username/password");
            } else {
                throw new BadRequestException("Account locked due to multiple failed login attempts");
            }
        }

        userInfo.setPasswordAttemptCount(0);
        userRepository.save(userInfo);
        List<RoleDto> roleDtos = roleMapper.toRoleDtos(userInfo.getRoles());
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roleDtos);

        if (roleDtos.stream().anyMatch(role -> role.getRole().toUpperCase().equals(UserRoles.STUDENT.name()))) {
            StudentDto studentDto = studentService.findByUserId(userInfo.getId());
            if (studentDto != null) {
                claims.put("studentId", studentDto.getIdNumber());
            }
        }
        String token = jwtService.generateToken(claims, user.get());
        return AuthenticationResponse.builder().token(token).build();
    }
}
