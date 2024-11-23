package com.technovate.school_management.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.technovate.school_management.entity.FileUpload;
import com.technovate.school_management.repository.FileUploadRepository;
import com.technovate.school_management.service.contracts.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final Cloudinary cloudinary;
    private final FileUploadRepository fileUploadRepository;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "EduTech"));
        String url = (String) uploadResult.get("url");
        String publicId = (String) uploadResult.get("public_id");
        FileUpload fileUpload = new FileUpload();
        fileUpload.setUrl(url);
        fileUpload.setPublicId(publicId);
        fileUploadRepository.save(fileUpload);
        return url;
    }

    @Override
    public void deleteFile(String url) {
        Optional<FileUpload> fileUploadOpt = fileUploadRepository.findByUrl(url);
        if (fileUploadOpt.isEmpty()) {
            return;
        }
        fileUploadRepository.delete(fileUploadOpt.get());
    }
}
