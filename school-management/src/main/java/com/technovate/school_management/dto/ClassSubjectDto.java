package com.technovate.school_management.dto;

import com.technovate.school_management.entity.SchoolClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassSubjectDto {
    private Long id;
    private String subjectCode;
    private SubjectDto subject;
    private SchoolClassDto schoolClass;
}
