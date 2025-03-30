package org.example.locket_clone_backend.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.example.locket_clone_backend.domain.dto.PostDto;
import org.example.locket_clone_backend.service.AuthService;
import org.example.locket_clone_backend.service.PostService;

@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final AuthService authService;

	@GetMapping("/posts")
	public Page<PostDto> getAllPosts(
			@RequestHeader("Authorization") String token,
			Pageable pageable
	) {
		Long userId = authService.getCurrentUser().id;
		return postService.getAllPosts(pageable, userId);
		
	}

}
