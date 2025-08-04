package org.example.locket_clone_backend.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import org.example.locket_clone_backend.domain.dto.AddImageDto;
import org.example.locket_clone_backend.domain.dto.AllPostsRes;
import org.example.locket_clone_backend.domain.dto.PostDto;
import org.example.locket_clone_backend.domain.dto.UserDto;
import org.example.locket_clone_backend.domain.entity.PostEntity;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
  public PostDto createPost(AddImageDto imageDto);

  // public Page<PostDto> getAllPosts(Pageable pageable, Long userId);

  public AllPostsRes getPostsKeyset(Long userId, OffsetDateTime cursorCreatedAt, int limit);

  public void interactPost(UserDto userDto, Long postId, String emoji);
  // public String uploadToCloudinary(MultipartFile file);
}
