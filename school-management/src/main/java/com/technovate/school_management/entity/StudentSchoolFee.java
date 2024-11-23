package com.technovate.school_management.entity;

import com.technovate.school_management.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "student_school_fee")
@Getter
@Setter
public class StudentSchoolFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    @Column(name = "total_due_amount")
    private double totalDueAmount;
    @ManyToOne
    @JoinColumn(name = "school_fee_id")
    private SchoolFee schoolFee;
    @Column(name = "payed_on")
    private LocalDateTime payedOn;
    @Column(name = "payment_reference")
    private String paymentReference;
}
