package com.technovate.school_management.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassSubjectSeedData {
    private Long classId;
    private List<Long> subjectIds;
    private String subjectCodeSuffix;
}
