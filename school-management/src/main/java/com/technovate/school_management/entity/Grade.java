package com.technovate.school_management.entity;

import com.technovate.school_management.entity.enums.GradeOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "student_score")
    private double studentScore;
    @ManyToOne
    @JoinColumn(name = "result_id")
    private Result result;
    @ManyToOne
    @JoinColumn(name = "class_subject_id")
    private ClassSubject classSubject;
    @Enumerated(EnumType.STRING)
    @Column(name = "grade_type")
    private GradeOption gradeType;
}
