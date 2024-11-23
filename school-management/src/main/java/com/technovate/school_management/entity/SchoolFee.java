package com.technovate.school_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "school_fee")
@Getter
@Setter
public class SchoolFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "fee_id")
    private Fee fee;
    @ManyToOne
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;
    @ManyToOne
    @JoinColumn(name = "term_id")
    private Term term;
}
