package com.technovate.school_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "class_subject")
public class ClassSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    @Column(name = "course_code")
    private String subjectCode;
}
