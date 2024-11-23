package com.technovate.school_management.service.contracts;

import com.technovate.school_management.dto.CreateFeeDto;
import com.technovate.school_management.dto.FeeDto;
import com.technovate.school_management.dto.FeeListDto;
import org.springframework.data.domain.Page;

public interface FeeService {
    void createFeeWithFeeItems(CreateFeeDto createFeeDto);
    FeeDto findFeeById(Long id);
    Page<FeeListDto> findAll(int page, int size);
}
