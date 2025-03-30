package org.example.locket_clone_backend.service;


import java.util.List;

import org.example.locket_clone_backend.domain.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
	public PostDto createPost(PostDto postDto);

	public Page<PostDto> getAllPosts(Pageable pageable, Long userId);
}
