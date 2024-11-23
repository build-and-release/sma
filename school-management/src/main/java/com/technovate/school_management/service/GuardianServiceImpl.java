package com.technovate.school_management.service;

import com.mailjet.client.errors.MailjetException;
import com.technovate.school_management.dto.CreateGuardianDto;
import com.technovate.school_management.entity.Guardian;
import com.technovate.school_management.entity.User;
import com.technovate.school_management.entity.enums.UserRoles;
import com.technovate.school_management.exception.BadRequestException;
import com.technovate.school_management.mapper.GuardianMapper;
import com.technovate.school_management.model.EmailModel;
import com.technovate.school_management.repository.GuardianRepository;
import com.technovate.school_management.service.contracts.EmailService;
import com.technovate.school_management.service.contracts.GuardianService;
import com.technovate.school_management.service.contracts.UserService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuardianServiceImpl implements GuardianService  {
    private final GuardianRepository guardianRepository;
    private final GuardianMapper guardianMapper;
    private final UserService userService;
    private final EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(GuardianService.class);

    @Value("${application_config.default_password}")
    private String DEFAULT_PASSWORD;

    @Override
    public void addGuardian(CreateGuardianDto createGuardianDto) {
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        var violations = validator.validate(createGuardianDto);

        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            violations.forEach(v -> stringBuilder.append(v.getMessage()).append("; "));
            throw new BadRequestException(stringBuilder.toString());
        }

        Optional<Guardian> existingGuardianOpt = guardianRepository.findByEmailOrPhoneNumber(
                createGuardianDto.getEmail(),
                createGuardianDto.getPhoneNumber()
        );

        if (existingGuardianOpt.isPresent()) {
            throw new BadRequestException("A guardian already exist with this email or phone number");
        }

        Guardian guardian = guardianMapper.toGuardian(createGuardianDto);
        User user = userService.createUserWithRoles(
                createGuardianDto.getEmail(),
                DEFAULT_PASSWORD,
                new ArrayList<>(Arrays.asList(UserRoles.USER, UserRoles.GUARDIAN))
        );
        guardian.setUser(user);
        guardianRepository.save(guardian);
                String emailBody = "<h3>Dear Parents/Guardians, </h3>" +
                "<p>This is to notify you have been successfully enrolled in our student management portal.</p>"
                + "<p>Your credentials are: <br> Username: " + createGuardianDto.getEmail()
                + "<br>Password: " + DEFAULT_PASSWORD;
        EmailModel email = new EmailModel(createGuardianDto.getEmail(), "Onboarding Completed", emailBody);
        try {
            emailService.sendEmail(email);
        } catch (MailjetException ex) {
            logger.error("Attempt to send email with this data: {} failed with the exception: {}", email, ex.toString());
        }
    }
}
