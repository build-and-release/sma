package com.technovate.school_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "file_upload")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileUpload {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "url")
    private String url;
    @Column(name = "public_id")
    private String publicId;
}
