package org.example.locket_clone_backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import org.apache.http.HttpStatus;
import org.example.locket_clone_backend.domain.dto.AddImageDto;
import org.example.locket_clone_backend.domain.dto.AllPostsRes;
import org.example.locket_clone_backend.domain.dto.PostDto;
import org.example.locket_clone_backend.domain.dto.UserDto;
import org.example.locket_clone_backend.service.AuthService;
import org.example.locket_clone_backend.service.ImageService;
import org.example.locket_clone_backend.service.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@Log
public class PostController {

	private final PostService postService;
	private final AuthService authService;
	private final ImageService imageService;

	// @GetMapping("/posts")
	// public Page<PostDto> getAllPosts(
	// Pageable pageable) {
	// Long userId = authService.getCurrentUser().id;
	// return postService.getAllPosts(pageable, userId);
	// }

	@GetMapping("/posts")
	@Operation(summary = "Get posts")

	public ResponseEntity<AllPostsRes> getPostsKeyset(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime cursorCreatedAt,
			@RequestParam(defaultValue = "10") int limit) {
		log.info("cursorCreatedAt = {} " + cursorCreatedAt);
		Long userId = authService.getCurrentUser().id;
		AllPostsRes posts = postService.getPostsKeyset(userId, cursorCreatedAt, limit);
		return ResponseEntity.ok(posts);
	}

	@PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Add post")

	public ResponseEntity<?> uploadImage(
			@RequestParam MultipartFile file,
			@RequestParam String caption,
			// @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant createdAt,
			@RequestParam boolean flip) {
		UserDto user = authService.getCurrentUser();
		String imageUrl = imageService.uploadToCloud(file);
		PostDto postDto = PostDto.builder()
				.caption(caption)
				.imageUrl(imageUrl)
				.createdAt(Instant.now())
				.user(user)
				.build();
		postService.createPost(postDto);
		return ResponseEntity.status(HttpStatus.SC_CREATED).build();
	}

}
