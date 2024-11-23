package com.technovate.school_management.service.contracts;

import com.technovate.school_management.dto.GetByStudentIdNumberDto;
import com.technovate.school_management.dto.StudentDto;
import com.technovate.school_management.dto.CreateStudentDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface StudentService {
    void OnboardStudent(CreateStudentDto createStudentDto);
    void uploadPassport(Long studentId, MultipartFile passportFile);
    StudentDto findStudentWithClassById(Long id);
    Page<StudentDto> findAll(int page, int size);
    StudentDto findByUserId(Long userId);
    StudentDto findStudentByIdNumber(GetByStudentIdNumberDto getByStudentIdNumberDto);
}
