package com.technovate.school_management.controller;

import com.technovate.school_management.dto.CreateTermDto;
import com.technovate.school_management.dto.TermDto;
import com.technovate.school_management.model.ApiResponseType;
import com.technovate.school_management.service.contracts.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/term")
@RequiredArgsConstructor
public class TermController {
    private final TermService termService;

    @GetMapping("")
    ResponseEntity<ApiResponseType<Page<TermDto>>> findAllTerms(
            @RequestParam(required = false, defaultValue =  "0") int page,
            @RequestParam(required = false, defaultValue =  "20")int size
    ) {
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(termService.findTerms(page, size)));
    }

    @PostMapping("")
    ResponseEntity<ApiResponseType<String>> addTerm(@RequestBody CreateTermDto createTermDto) {
        termService.createNewTerm(createTermDto);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(null, "Term created successfully"));
    }
}
