package com.technovate.school_management.controller;

import com.technovate.school_management.dto.GetByStudentIdNumberDto;
import com.technovate.school_management.dto.ResultListDto;
import com.technovate.school_management.model.ApiResponseType;
import com.technovate.school_management.service.contracts.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/result")
@RequiredArgsConstructor
public class ResultController {
    private final ResultService resultService;
    @PostMapping("student")
    ResponseEntity<ApiResponseType<List<ResultListDto>>> getResultsByStudentIdNumber(@RequestBody GetByStudentIdNumberDto getByStudentIdNumberDto) {
        List<ResultListDto> resultListDtos = resultService.findResultsByStudentIdNumber(getByStudentIdNumberDto);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(resultListDtos));
    };
}
