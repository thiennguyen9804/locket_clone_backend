package org.example.locket_clone_backend.service.impl;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.example.locket_clone_backend.domain.dto.AllPostsRes;
import org.example.locket_clone_backend.domain.dto.PostDto;
import org.example.locket_clone_backend.domain.dto.UserDto;
import org.example.locket_clone_backend.domain.entity.InteractionEntity;
import org.example.locket_clone_backend.domain.entity.PostEntity;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.mapper.Mapper;
import org.example.locket_clone_backend.repository.InteractionRepository;
import org.example.locket_clone_backend.repository.PostRepository;
import org.example.locket_clone_backend.service.PostService;
import org.example.locket_clone_backend.utils.specs.PostSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@RequiredArgsConstructor
@Log
public class PostServiceImpl implements PostService {
  private final PostRepository postRepo;
  private final Mapper<PostEntity, PostDto> postMapper;

  private final Mapper<UserEntity, UserDto> userMapper;

  private final InteractionRepository interactionRepository;

  public PostDto createPost(PostDto postDto) {
    PostEntity entity = postMapper.mapFrom(postDto);
    final PostEntity saved = postRepo.save(entity);
    final PostDto res = postMapper.mapTo(saved);
    return res;
  }

  // @Override
  // public Page<PostDto> getAllPosts(Pageable pageable, Long userId) {
  // Page<PostEntity> res =
  // postRepo.findAll(PostSpecs.isPostBelongToFriendOrSelf(userId), pageable);
  // return res.map(postMapper::mapTo);
  // }

  @Override
  public AllPostsRes getPostsKeyset(Long userId, OffsetDateTime cursorCreatedAt, int limit) {
    log.info("PostServiceImpl getPostsKeyset = {} " + cursorCreatedAt);
    List<PostEntity> result = postRepo.findPostsKeyset(userId, cursorCreatedAt, limit);
    List<PostDto> postList = result.stream().map(postMapper::mapTo).collect(Collectors.toList());
    Long count = postRepo.countAllPosts(userId);
    return AllPostsRes.builder().content(postList).totalElements(count).build();
  }

  @Override
  public void interactPost(UserDto userDto, Long postId, String emoji) {
    PostEntity postEntity = postRepo.findById(postId).get();
    UserEntity userEntity = userMapper.mapFrom(userDto);
    InteractionEntity entity = InteractionEntity.builder()
        .emoji(emoji)
        .user(userEntity)
        .post(postEntity)
        .build();
  }

}
