package org.example.locket_clone_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.example.locket_clone_backend.domain.dto.PostDto;
import org.example.locket_clone_backend.domain.entity.PostEntity;
import org.example.locket_clone_backend.mapper.Mapper;
import org.example.locket_clone_backend.repository.PostRepository;
import org.example.locket_clone_backend.service.PostService;
import org.example.locket_clone_backend.utils.specs.PostSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	private final PostRepository postRepo;
	private final Mapper<PostEntity, PostDto> mapper;

	public PostDto createPost(PostDto postDto) {
		PostEntity entity = mapper.mapFrom(postDto);
		final PostEntity saved = postRepo.save(entity);
		final PostDto res = mapper.mapTo(saved);
		return res;
	}

	@Override
	public Page<PostDto> getAllPosts(Pageable pageable, Long userId) {
		Page<PostEntity> res = postRepo.findAll(PostSpecs.isPostBelongToFriendOrSelf(userId), pageable);
		return res.map(mapper::mapTo);
	}

}
