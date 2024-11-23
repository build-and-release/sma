package com.technovate.school_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_class")
public class StudentClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @Column(name = "is_current")
    private boolean isCurrent;
}
