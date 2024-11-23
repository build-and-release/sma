package com.technovate.school_management.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolClassSeedModel {
    private Long Id;
    private String name;
    private String level;
    private Long nextClassId;
}
