package com.technovate.school_management.controller;

import com.cloudinary.Api;
import com.technovate.school_management.dto.ClassSubjectDto;
import com.technovate.school_management.dto.CreateClassSubjectDto;
import com.technovate.school_management.model.ApiResponseType;
import com.technovate.school_management.service.contracts.ClassSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/class-subject")
@RequiredArgsConstructor
public class ClassSubjectController {
    private final ClassSubjectService classSubjectService;
    @PostMapping("")
    ResponseEntity<ApiResponseType<String>> addSubjectToClass(@RequestBody CreateClassSubjectDto createClassSubjectDto) {
        classSubjectService.addSubjectToClass(createClassSubjectDto);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(null, "Subject add to class"));
    }

    @GetMapping("/")
    ResponseEntity<ApiResponseType<Page<ClassSubjectDto>>> findAllClassSubjects(
            @RequestParam(required = false, defaultValue =  "0") int page,
            @RequestParam(required = false, defaultValue =  "10")int size
    ) {
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(classSubjectService.findAllClassSubjects(page, size)));
    }

    @GetMapping("/{schoolClassName}")
    ResponseEntity<ApiResponseType<List<ClassSubjectDto>>> findAllByClassname(@PathVariable String schoolClassName) {
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(classSubjectService.findBySchoolClassName(schoolClassName)));
    }
}
