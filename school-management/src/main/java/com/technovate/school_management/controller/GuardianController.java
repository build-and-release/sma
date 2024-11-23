package com.technovate.school_management.controller;

import com.technovate.school_management.dto.CreateGuardianDto;
import com.technovate.school_management.model.ApiResponseType;
import com.technovate.school_management.service.contracts.GuardianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/guardian")
@RequiredArgsConstructor
public class GuardianController {
    private final GuardianService guardianService;

    @PostMapping("")
    public ResponseEntity<ApiResponseType<String>> addGuardian(@RequestBody CreateGuardianDto createGuardianDto) {
        guardianService.addGuardian(createGuardianDto);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(null, "Guardian onboarded successfully"));
    }
}
