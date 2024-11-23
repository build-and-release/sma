package com.technovate.school_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "result")
@Getter
@Setter
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @OneToMany(mappedBy = "result")
    private List<Grade> grade;
    @ManyToOne
    @JoinColumn(name = "student_class_id")
    private StudentClass studentClass;
    @ManyToOne
    @JoinColumn(name = "term_id")
    private Term term;
}
