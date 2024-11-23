package com.technovate.school_management.controller;

import com.technovate.school_management.dto.GradeDto;
import com.technovate.school_management.dto.GradeListDto;
import com.technovate.school_management.model.ApiResponseType;
import com.technovate.school_management.service.contracts.GradeService;
import com.technovate.school_management.dto.CreateGradeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grade")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @PostMapping("")
    public ResponseEntity<ApiResponseType<?>> createGrade(@RequestBody CreateGradeDto createGradeDto) {
        gradeService.createGrade(createGradeDto);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(null, "Create added successfully"));
    }

    @GetMapping("{resultId}")
    public ResponseEntity<ApiResponseType<List<GradeListDto>>> getGradesByResultId(@PathVariable Long resultId) {
        List<GradeListDto> gradeDtos = gradeService.findGradesByResultId(resultId);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(gradeDtos));
    }
}
