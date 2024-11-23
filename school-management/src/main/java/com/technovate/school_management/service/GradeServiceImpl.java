package com.technovate.school_management.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technovate.school_management.dto.CreateGradeDto;
import com.technovate.school_management.dto.GradeDto;
import com.technovate.school_management.dto.GradeListDto;
import com.technovate.school_management.entity.*;
import com.technovate.school_management.entity.enums.GradeOption;
import com.technovate.school_management.exception.BadRequestException;
import com.technovate.school_management.exception.ResourceNotFoundException;
import com.technovate.school_management.mapper.GradeMapper;
import com.technovate.school_management.model.GradeConfiguration;
import com.technovate.school_management.repository.*;
import com.technovate.school_management.service.contracts.GradeService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService  {
    private final GradeRepository gradeRepository;
    private final ResultRepository resultRepository;
    private final StudentRepository studentRepository;
    private final ClassSubjectRepository classSubjectRepository;
    private final StudentClassRepository studentClassRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TermRepository termRepository;
    private final GradeMapper gradeMapper;

    private void processGrade(CreateGradeDto createGradeDto) {
        //        Find student from student Id number
        Optional<Student> studentOpt = studentRepository.findByIdNumber(createGradeDto.getStudentIdNumber());
        if (studentOpt.isEmpty()) {
            throw new ResourceNotFoundException("Student with id " + createGradeDto.getStudentIdNumber() + " not found");
        }
//        Get current term
        Optional< Term> currentTermOpt = termRepository.findByIsCurrentTrue();
        if (currentTermOpt.isEmpty()) {
            throw new BadRequestException("Unable to get current term");
        }

        Term currentTerm = currentTermOpt.get();

//        check if the class subject is for the student's current class
        Optional<ClassSubject> classSubjectOpt = classSubjectRepository.findById(createGradeDto.getClassSubjectId());

        if (classSubjectOpt.isEmpty()) {
            throw new ResourceNotFoundException("Class subject with id " + createGradeDto.getClassSubjectId() + " does not exist");
        }

        ClassSubject classSubject = classSubjectOpt.get();
        Student student = studentOpt.get();
        Optional<StudentClass> studentClassOpt = studentClassRepository.findFirstByStudentIdAndIsCurrent(student.getId(), true);
        if (studentClassOpt.isEmpty()) {
            throw new BadRequestException("Unable to retrieve student's current class");
        }
//        do the actual comparison between the class subject and student class
        StudentClass studentClass = studentClassOpt.get();
        if (!classSubject.getSchoolClass().getId().equals(studentClass.getSchoolClass().getId())) {
            throw new BadRequestException("You can only assign grades to students current class");
        }

//        check if the student has a result for the current class
        Result result;
        Optional<Result> resultOpt = resultRepository.findByStudentId(student.getId());
        if (resultOpt.isPresent()) {
            result = resultOpt.get();
        } else {
            result = new Result();
            result.setTerm(currentTerm);
            result.setStudent(student);
            result.setStudentClass(studentClass);
            resultRepository.save(result);
        }

//        check if there is already a grade for that course and override it
        Grade grade;
        Optional<Grade> gradeExistOpt = gradeRepository.findByResultIdAndClassSubjectId(result.getId(), classSubject.getId());
        if (gradeExistOpt.isPresent()) {
            grade = gradeExistOpt.get();
        } else {
            grade = new Grade();
            grade.setResult(result);
            grade.setClassSubject(classSubject);
        }
        grade.setStudentScore(createGradeDto.getStudentScore());
        try {
            grade.setGradeType(processGrade(createGradeDto.getStudentScore()));
        } catch (IOException ex) {
            System.out.println("Unable to process grade " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        gradeRepository.save(grade);
    }

    @Override
    public void createGrade(CreateGradeDto createGradeDto) {
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        var violations = validator.validate(createGradeDto);

        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            violations.forEach(v -> stringBuilder.append(v.getMessage()).append("; "));
            throw new BadRequestException(stringBuilder.toString());
        }
        this.processGrade(createGradeDto);
    }

    @Override
    public List<GradeListDto> findGradesByResultId(Long resultId) {
        List<Grade> grades = gradeRepository.findByResultId(resultId);
        return gradeMapper.toGradeListDtos(grades);
    }

    @Override
    public void createGradeForStudent(String studentIdNumber, Long classId) {
        //       verify if the student exists
        Optional<Student> studentOpt = studentRepository.findByIdNumber(studentIdNumber);
        if (studentOpt.isEmpty()) {
            throw new ResourceNotFoundException("Student with id " + studentIdNumber + " not found");
        }
//        Verify if the class exists
        Optional<SchoolClass> schoolClassOpt = schoolClassRepository.findById(classId);
        if (schoolClassOpt.isEmpty()) {
            throw new ResourceNotFoundException("School class not found");
        }
        SchoolClass schoolClass = schoolClassOpt.get();
//        Get all class subjects for the school class
        List<ClassSubject> classSubjects = classSubjectRepository.findBySchoolClassName(schoolClass.getName());
        if (classSubjects.isEmpty()) {
            throw new BadRequestException("No subject to be added");
        }

        for (ClassSubject subject : classSubjects) {
            CreateGradeDto createGradeDto = new CreateGradeDto();
            createGradeDto.setStudentIdNumber(studentIdNumber);
            createGradeDto.setClassSubjectId(subject.getId());
            this.processGrade(createGradeDto);
        }

    }

    private GradeOption processGrade(double score) throws IOException {
        GradeConfiguration gradeConfiguration = loadGradeConfigurationFromJson();

        if (score >= gradeConfiguration.getA()) {
            return GradeOption.A;
        }
        if (score >= gradeConfiguration.getB()) {
            return GradeOption.B;
        }
        if (score >= gradeConfiguration.getC()) {
            return GradeOption.C;
        }
        if (score >= gradeConfiguration.getD()) {
            return GradeOption.D;
        }
        if (score >= gradeConfiguration.getE()) {
            return GradeOption.E;
        }
        return GradeOption.F;
    };

    private GradeConfiguration loadGradeConfigurationFromJson() throws IOException {
        // Load JSON from resources
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                new ClassPathResource("grade-configuration.json").getInputStream(),
                new TypeReference<GradeConfiguration>() {}
        );
    }
}
