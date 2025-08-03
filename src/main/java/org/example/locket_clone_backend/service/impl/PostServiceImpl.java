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
import org.example.locket_clone_backend.domain.entity.post_visibility_entity.PostVisibilityEntity;
import org.example.locket_clone_backend.domain.entity.post_visibility_entity.PostVisibilityId;
import org.example.locket_clone_backend.domain.entity.relationship_entity.Relationship;
import org.example.locket_clone_backend.domain.entity.relationship_entity.RelationshipEntity;
import org.example.locket_clone_backend.mapper.Mapper;
import org.example.locket_clone_backend.repository.InteractionRepository;
import org.example.locket_clone_backend.repository.PostRepository;
import org.example.locket_clone_backend.repository.PostVisibilityRepository;
import org.example.locket_clone_backend.repository.RelationshipRepository;
import org.example.locket_clone_backend.service.PostService;
import org.example.locket_clone_backend.service.UserService;
import org.example.locket_clone_backend.utils.specs.PostSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.memory.UserAttributeEditor;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@RequiredArgsConstructor
@Log
public class PostServiceImpl implements PostService {
  private final PostRepository postRepo;
  private final InteractionRepository interactionRepository;
  private final PostVisibilityRepository postVisibilityRepository;
  private final RelationshipRepository relationshipRepository;

  private final Mapper<PostEntity, PostDto> postMapper;
  private final Mapper<UserEntity, UserDto> userMapper;

  private final UserService userService;

  public PostDto createPost(PostDto postDto) {
    PostEntity entity = postMapper.mapFrom(postDto);
    final PostEntity saved = postRepo.save(entity);
    final Long userId = saved.user.id;

    List<RelationshipEntity> results = relationshipRepository.findFriendsByRelationship(userId, Relationship.FRIEND);
    List<UserEntity> friendList = results.stream().map((RelationshipEntity rela) -> {
      if (rela.getUser1().id != userId) {
        return rela.getUser1();
      } else {
        return rela.getUser2();

      }
    }).toList();
    for (UserEntity friend : friendList) {
      log.info("🚀 ~ PostServiceImpl ~ List<UserEntity>getFriends ~ res: {} " + friend);
    }

    bulkAddPost(friendList, saved);
    final PostDto res = postMapper.mapTo(saved);
    return res;
  }

  private void bulkAddPost(List<UserEntity> users, PostEntity post) {
    List<PostVisibilityEntity> visibilities = users.stream()
        .map(user -> PostVisibilityEntity.builder()
            .id(new PostVisibilityId(user.id, post.id))
            .user(user)
            .post(post)
            .build())
        .toList();
    for (PostVisibilityEntity postIter : visibilities) {

      log.info("PostServiceImpl bulkAddPost postVisbility = {} " + postIter);
    }

    postVisibilityRepository.saveAll(visibilities);
  }

  @Override
  public AllPostsRes getPostsKeyset(Long userId, OffsetDateTime cursorCreatedAt, int limit) {
    log.info("PostServiceImpl getPostsKeyset = {} " + cursorCreatedAt);
    List<PostEntity> result = postRepo.findPostsKeyset(userId, cursorCreatedAt, limit);
    // for (PostEntity entity : result) {
    //
    // log.info("PostServiceImpl getPostsKeyset postEntity = {} " + entity);
    // }
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
