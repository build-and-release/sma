package com.technovate.school_management.entity;

import com.technovate.school_management.entity.enums.UserRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    public Role(UserRoles role) {
        this.role = role;
    }
}
