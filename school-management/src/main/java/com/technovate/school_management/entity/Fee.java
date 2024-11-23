package com.technovate.school_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "fee")
@Getter
@Setter
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "late_fee")
    private double lateFee;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @OneToMany(mappedBy = "fee")
    private List<FeeItem> feeItems;
    @Column(name = "total")
    private double total;
}
