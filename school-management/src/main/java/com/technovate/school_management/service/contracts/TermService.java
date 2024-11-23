package com.technovate.school_management.service.contracts;

import com.technovate.school_management.dto.CreateTermDto;
import com.technovate.school_management.dto.TermDto;
import org.springframework.data.domain.Page;

public interface TermService {
    void createNewTerm(CreateTermDto createTermDto);
    Page<TermDto> findTerms(int page, int size);
}
