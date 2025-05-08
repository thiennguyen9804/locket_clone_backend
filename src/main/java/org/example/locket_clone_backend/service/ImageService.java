package org.example.locket_clone_backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public String uploadToCloud(MultipartFile file);
}
