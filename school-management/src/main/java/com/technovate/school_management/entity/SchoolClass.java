package com.technovate.school_management.entity;

import com.technovate.school_management.entity.enums.ClassEnum;
import com.technovate.school_management.entity.enums.ClassLevelEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "school_class")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private ClassEnum name;
    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private ClassLevelEnum level;
    @OneToOne
    @JoinColumn(name = "next_class_id")
    private SchoolClass nextClass;
}
