package org.example.locket_clone_backend.service.impl;

import org.example.locket_clone_backend.service.ImageService;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final Cloudinary cloudinary;

}
