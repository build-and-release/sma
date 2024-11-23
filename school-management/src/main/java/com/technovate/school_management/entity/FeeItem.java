package com.technovate.school_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fee_item")
@Getter
@Setter
public class FeeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "fee_id")
    private Fee fee;
    @Column(name = "amount")
    private double amount;
}
