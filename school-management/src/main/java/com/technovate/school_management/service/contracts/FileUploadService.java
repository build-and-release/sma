package com.technovate.school_management.service.contracts;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    String uploadFile(MultipartFile file) throws IOException;
    void deleteFile(String url);
}
