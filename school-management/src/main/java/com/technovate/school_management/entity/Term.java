package com.technovate.school_management.entity;

import com.technovate.school_management.entity.enums.SchoolTerm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "term")
@Getter
@Setter
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private SchoolTerm name;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "is_current")
    private boolean isCurrent;
}
