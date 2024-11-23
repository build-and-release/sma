package com.technovate.school_management.entity;

import com.technovate.school_management.entity.enums.DataStatus;
import com.technovate.school_management.entity.enums.GenderEnum;
import com.technovate.school_management.entity.enums.GuardianType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "student")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "address")
    private String address;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @OneToMany(
            mappedBy = "student",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}
    )
    private List<StudentClass> studentClass;
    @Column(name = "id_number")
    private String idNumber;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    @ManyToOne
    @JoinColumn(name = "guardian_id")
    private Guardian guardian;
    @Enumerated(EnumType.STRING)
    @Column(name = "guardian_type")
    private GuardianType guardianType;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DataStatus status = DataStatus.ACTIVE;
    @Column(name = "passport_url")
    private String passportUrl;
    @Column(name = "nationality")
    private String nationality;
}
