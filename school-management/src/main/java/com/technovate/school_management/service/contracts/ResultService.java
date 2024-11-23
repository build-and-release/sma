package com.technovate.school_management.service.contracts;

import com.technovate.school_management.dto.GetByStudentIdNumberDto;
import com.technovate.school_management.dto.ResultListDto;

import java.util.List;

public interface ResultService {
    List<ResultListDto> findResultsByStudentIdNumber(GetByStudentIdNumberDto getByStudentIdNumberDto);
}
