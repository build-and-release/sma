package com.technovate.school_management.repository;

import com.technovate.school_management.entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
    Optional<FileUpload> findByUrl(String url);
}
