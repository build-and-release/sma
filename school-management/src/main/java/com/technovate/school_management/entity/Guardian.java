package com.technovate.school_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "guardian")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Guardian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "alternate_phone_number")
    private String alternatePhoneNumber;
    @Column(name = "nationality")
    private String nationality;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;
}
