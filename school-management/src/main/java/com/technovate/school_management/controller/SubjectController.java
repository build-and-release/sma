package com.technovate.school_management.controller;

import com.technovate.school_management.dto.CreateSubjectDto;
import com.technovate.school_management.dto.SubjectDto;
import com.technovate.school_management.model.ApiResponseType;
import com.technovate.school_management.service.contracts.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping("/")
    public ResponseEntity<ApiResponseType<String>> addSubject(@RequestBody CreateSubjectDto createSubjectDto) {
        subjectService.addSubject(createSubjectDto);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(null, "Subject added"));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseType<Page<SubjectDto>>> getAll(
            @RequestParam(required = false, defaultValue =  "0") int page,
            @RequestParam(required = false, defaultValue =  "10")int size
    ) {
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(subjectService.findSubjects(page, size)));
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<ApiResponseType<List<SubjectDto>>> getByClassLevel(@PathVariable String level) {
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(subjectService.findAllByLevel(level)));
    }
}
