package org.example.locket_clone_backend.service.impl;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.example.locket_clone_backend.domain.dto.AllPostsRes;
import org.example.locket_clone_backend.domain.dto.PostDto;
import org.example.locket_clone_backend.domain.entity.PostEntity;
import org.example.locket_clone_backend.mapper.Mapper;
import org.example.locket_clone_backend.repository.PostRepository;
import org.example.locket_clone_backend.service.PostService;
import org.example.locket_clone_backend.utils.specs.PostSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@RequiredArgsConstructor
@Log
public class PostServiceImpl implements PostService {
	private final PostRepository postRepo;
	private final Mapper<PostEntity, PostDto> mapper;
	private final Cloudinary cloudinary;

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

	@Override
	public String uploadToCloudinary(MultipartFile file) {
		Map uploadResult = null;
		try {
			uploadResult = cloudinary.uploader()
					.upload(file.getBytes(), ObjectUtils.emptyMap());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String) uploadResult.get("secure_url");
	}

	@Override
	public AllPostsRes getPostsKeyset(Long userId, OffsetDateTime cursorCreatedAt, int limit) {
		log.info("PostServiceImpl getPostsKeyset = {} " + cursorCreatedAt);
		List<PostEntity> result = postRepo.findPostsKeyset(userId, cursorCreatedAt, limit);
		List<PostDto> postList = result.stream().map(mapper::mapTo).collect(Collectors.toList());
		Long count = postRepo.countAllPosts(userId);
		return AllPostsRes.builder().content(postList).totalElements(count).build();
	}

}
