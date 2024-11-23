package com.technovate.school_management.dto;

import com.technovate.school_management.entity.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto {
    private Long id;
    private double studentScore;
    private Result result;
    private ClassSubjectDto classSubject;
    private String gradeType;
}
