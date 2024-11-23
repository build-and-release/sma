package com.technovate.school_management.service;

import com.technovate.school_management.dto.GetByStudentIdNumberDto;
import com.technovate.school_management.dto.ResultListDto;
import com.technovate.school_management.dto.StudentDto;
import com.technovate.school_management.entity.Result;
import com.technovate.school_management.exception.ResourceNotFoundException;
import com.technovate.school_management.mapper.ResultMapper;
import com.technovate.school_management.repository.ResultRepository;
import com.technovate.school_management.service.contracts.ResultService;
import com.technovate.school_management.service.contracts.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;
    private final ResultMapper resultMapper;
    private final StudentService studentService;

    @Override
    public List<ResultListDto> findResultsByStudentIdNumber(GetByStudentIdNumberDto getByStudentIdNumberDto) {
//        Verify if the student exists
        StudentDto studentDto = studentService.findStudentByIdNumber(getByStudentIdNumberDto);
        if (studentDto == null) {
            throw new ResourceNotFoundException("Student with the id number " + getByStudentIdNumberDto.getIdNumber() + " was not found");
        }
        List<Result> results = resultRepository.findByStudentIdNumber(getByStudentIdNumberDto.getIdNumber());
        return resultMapper.toResultListDtos(results);
    }
}
