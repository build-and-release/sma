package com.technovate.school_management.controller;

import com.technovate.school_management.dto.CreateFeeDto;
import com.technovate.school_management.dto.FeeDto;
import com.technovate.school_management.dto.FeeListDto;
import com.technovate.school_management.model.ApiResponseType;
import com.technovate.school_management.service.contracts.FeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fee")
@RequiredArgsConstructor
public class FeeController {
    private final FeeService feeService;

    @GetMapping("")
    public ResponseEntity<ApiResponseType<Page<FeeListDto>>> findAll(
            @RequestParam(required = false, defaultValue =  "0") int page,
            @RequestParam(required = false, defaultValue =  "10")int size
    ) {
        Page<FeeListDto> fees = feeService.findAll(page, size);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(fees));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponseType<String>> createFee(@RequestBody CreateFeeDto createFeeDto) {
        feeService.createFeeWithFeeItems(createFeeDto);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse("Fee added successfully"));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponseType<FeeDto>> getById(@PathVariable Long id) {
        FeeDto feeDto = feeService.findFeeById(id);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(feeDto));
    }
}
