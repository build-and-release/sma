package com.technovate.school_management.service;

import com.mailjet.client.errors.MailjetException;
import com.technovate.school_management.dto.GetByStudentIdNumberDto;
import com.technovate.school_management.dto.StudentDto;
import com.technovate.school_management.dto.CreateStudentDto;
import com.technovate.school_management.entity.*;
import com.technovate.school_management.entity.enums.UserRoles;
import com.technovate.school_management.exception.BadRequestException;
import com.technovate.school_management.exception.ResourceNotFoundException;
import com.technovate.school_management.mapper.StudentMapper;
import com.technovate.school_management.model.EmailModel;
import com.technovate.school_management.repository.*;
import com.technovate.school_management.service.contracts.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final UsersCredentialsGenerator usersCredentialsGenerator;
    private final StudentMapper studentMapper;
    private final UserRepository userRepository;
    private final StudentClassRepository studentClassRepository;
    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final EmailService emailService;
    private final FileUploadService fileUploadService;
    private final UserService userService;
    private final GuardianRepository guardianRepository;
    private final GradeService gradeService;
    private final SchoolFeeRepository schoolFeeRepository;
    private final StudentSchoolFeeService studentSchoolFeeService;
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Value("${application_config.default_password}")
    private String DEFAULT_PASSWORD;

    @Override
    public void OnboardStudent(CreateStudentDto createStudentDto) {
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        var violations = validator.validate(createStudentDto);

        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            violations.forEach(v -> stringBuilder.append(v.getMessage()).append("; "));
            throw new BadRequestException(stringBuilder.toString());
        }
        Student student = studentMapper.toStudent(createStudentDto);
        Optional<Guardian> guardianOpt = guardianRepository.findByEmail(createStudentDto.getGuardianEmail());
        if (guardianOpt.isEmpty()) {
            throw new ResourceNotFoundException("Guardian with the email" + createStudentDto.getGuardianEmail() + " does not exist");
        }
        student.setGuardian(guardianOpt.get());
        String username = usersCredentialsGenerator.generateUsername(
                createStudentDto.getFirstName().toLowerCase(),
                createStudentDto.getLastName().toLowerCase()
        );
        String idNumber = usersCredentialsGenerator.generateIdNumber();
        student.setIdNumber(idNumber);
        Optional<SchoolClass> schoolClassOpt = schoolClassRepository.findById(createStudentDto.getClassId());
        if (schoolClassOpt.isEmpty()) {
            throw new ResourceNotFoundException("School class with id " + createStudentDto.getClassId() + " not found");
        }

        User user = userService.createUserWithRoles(
                username,
                DEFAULT_PASSWORD,
                new ArrayList<>(Arrays.asList(UserRoles.USER, UserRoles.STUDENT))
        );
        userRepository.save(user);
        student.setUser(user);

        studentRepository.save(student);
        StudentClass studentClass = new StudentClass();
        studentClass.setStudent(student);
        studentClass.setSchoolClass(schoolClassOpt.get());
        studentClass.setCurrent(true);
        studentClassRepository.save(studentClass);
//        Upload passport
        this.uploadPassport(student.getId(), createStudentDto.getPassportImage());
//        If a school Fee exist for the class already, find and add the school fee to the student
        studentSchoolFeeService.addSchoolFeeForStudent(student.getId(), createStudentDto.getClassId());
        String emailBody = "<h3>Dear Parents/Guardians, </h3>" +
                "<p>This is to notify you that your ward's has been successfully enrolled</p>"
                + "<p>Your wards credentials are: <br> Username: " + username
                + "<br>Password: " + DEFAULT_PASSWORD + "<br>Id Number: " + idNumber + "</p>" ;
        EmailModel email = new EmailModel(createStudentDto.getGuardianEmail(), "Onboarding Completed", emailBody);
        try {
            emailService.sendEmail(email);
        } catch (MailjetException ex) {
            logger.error("Attempt to send email with this data: {} failed with the exception: {}", email, ex.toString());
        }
        gradeService.createGradeForStudent(student.getIdNumber(), createStudentDto.getClassId());
    }

    @Override
    public void uploadPassport(Long studentId, MultipartFile passportFile) {
//        Find the student by id
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            logger.error("Unable to find student with the id: {}", studentId);
            return;
        }
        Student student = studentOpt.get();
        try {
            String passportUrl = fileUploadService.uploadFile(passportFile);
            student.setPassportUrl(passportUrl);
        } catch (IOException ex) {
            logger.error("Attempt to upload passport for student with the id: {} failed with the exception: {}", studentId, ex.toString());
        }
    }

    @Override
    public StudentDto findStudentWithClassById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(studentMapper::toStudentDto).orElse(null);
    }

    @Override
    public Page<StudentDto> findAll(int page, int size) {
        Page<Student> students = studentRepository.findAll(PageRequest.of(page, size));
        return studentMapper.toPageStudentDto(students);
    }

    @Override
    public StudentDto findByUserId(Long userId) {
        Optional<Student> student = studentRepository.findByUserId(userId);
        return student.map(studentMapper::toStudentDto).orElse(null);
    }

    @Override
    public StudentDto findStudentByIdNumber(GetByStudentIdNumberDto getByStudentIdNumberDto) {
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        var violations = validator.validate(getByStudentIdNumberDto);

        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            violations.forEach(v -> stringBuilder.append(v.getMessage()).append("; "));
            throw new BadRequestException(stringBuilder.toString());
        }
        Optional<Student> studentOpt = studentRepository.findByIdNumber(getByStudentIdNumberDto.getIdNumber());
        if (studentOpt.isEmpty()) {
            throw new ResourceNotFoundException("Student with the id number " + getByStudentIdNumberDto.getIdNumber() + " was not found");
        }
        return studentOpt.map(studentMapper::toStudentDto).orElse(null);
    }
}
