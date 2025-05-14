package org.example.locket_clone_backend.service.impl;

import java.io.IOException;
import java.util.Map;

import org.example.locket_clone_backend.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final Cloudinary cloudinary;

    @Override
    public String uploadToCloud(MultipartFile file) {
        Map uploadResult = null;
		try {
			uploadResult = cloudinary.uploader()
					.upload(file.getBytes(), ObjectUtils.emptyMap());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String) uploadResult.get("secure_url");
    }

}
