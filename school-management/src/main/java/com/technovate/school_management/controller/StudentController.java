package com.technovate.school_management.controller;

import com.technovate.school_management.dto.GetByStudentIdNumberDto;
import com.technovate.school_management.dto.StudentClassDto;
import com.technovate.school_management.dto.StudentDto;
import com.technovate.school_management.dto.CreateStudentDto;
import com.technovate.school_management.model.ApiResponseType;
import com.technovate.school_management.service.contracts.StudentClassService;
import com.technovate.school_management.service.contracts.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentClassService studentClassService;

    @GetMapping("")
    ResponseEntity<ApiResponseType<Page<StudentClassDto>>> findAllStudents(
            @RequestParam(required = false, defaultValue =  "0") int page,
            @RequestParam(required = false, defaultValue =  "10")int size
    ) {
        Page<StudentClassDto> studentDtos = studentClassService.findStudentsWithCurrentClass(page, size);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(studentDtos));
    }

    @PostMapping(value = "/", consumes = {"multipart/form-data"})
    ResponseEntity<ApiResponseType<String>> onboardStudent(@ModelAttribute CreateStudentDto createStudentDto) {
        studentService.OnboardStudent(createStudentDto);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(null,"Student onboarded successfully..."));
    }

    @GetMapping("{id}")
    ResponseEntity<ApiResponseType<StudentClassDto>> findStudentDetailById(@PathVariable Long id) {
        StudentClassDto student = studentClassService.findStudentByIdWithCurrentClass(id);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(student));
    }

    @PostMapping("get-by-id-number")
    ResponseEntity<ApiResponseType<StudentDto>> findStudentByIdNumber(@RequestBody GetByStudentIdNumberDto getByStudentIdNumberDto) {
        StudentDto studentDto = studentService.findStudentByIdNumber(getByStudentIdNumberDto);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(studentDto));
    }
}
