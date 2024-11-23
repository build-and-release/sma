package com.technovate.school_management.entity;

import com.technovate.school_management.entity.enums.ClassLevelEnum;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "subject_code")
    private String subjectCode;
    @Enumerated(EnumType.STRING)
    private ClassLevelEnum level;
}
