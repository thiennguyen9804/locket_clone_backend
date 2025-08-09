package org.example.locket_clone_backend.seed;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.example.locket_clone_backend.domain.dto.PostDto;
import org.example.locket_clone_backend.domain.dto.UserDto;
import org.example.locket_clone_backend.domain.entity.InteractionEntity;
import org.example.locket_clone_backend.domain.entity.PostEntity;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.domain.entity.message_entity.MessageEntity;
import org.example.locket_clone_backend.domain.entity.post_visibility_entity.PostVisibilityEntity;
import org.example.locket_clone_backend.domain.entity.post_visibility_entity.PostVisibilityId;
import org.example.locket_clone_backend.domain.entity.relationship_entity.Relationship;
import org.example.locket_clone_backend.domain.entity.relationship_entity.RelationshipEntity;
import org.example.locket_clone_backend.domain.entity.relationship_entity.RelationshipId;
import org.example.locket_clone_backend.mapper.Mapper;
import org.example.locket_clone_backend.mapper.impl.PostMapper;
import org.example.locket_clone_backend.repository.InteractionRepository;
import org.example.locket_clone_backend.repository.MessageRepository;
import org.example.locket_clone_backend.repository.PostRepository;
import org.example.locket_clone_backend.repository.PostVisibilityRepository;
import org.example.locket_clone_backend.repository.RelationshipRepository;
import org.example.locket_clone_backend.repository.UserRepository;
import org.example.locket_clone_backend.service.PostService;
import org.example.locket_clone_backend.service.UserService;
import org.example.locket_clone_backend.service.impl.PostServiceImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Component
@RequiredArgsConstructor
@Log
public class Seeding implements ApplicationRunner {

  private UserEntity user1, user2;
  private PostEntity post1, post2;
  private Instant now;
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final RelationshipRepository relationshipRepository;
  private final InteractionRepository interactionRepository;
  private final PostVisibilityRepository postVisibilityRepository;
  private final PasswordEncoder bEncoder;
  private final UserService userService;
  private final Mapper<UserEntity, UserDto> userMapper;
  private final PostService postService;
  private final PostMapper postMapper;
  private final MessageRepository messageRepository;

  List<PostEntity> posts = new ArrayList<>();

  @Override
  public void run(ApplicationArguments args) throws Exception {
    try {
      createUser();
      addFriend();
      seedingPost();
      seedInteraction();
      seedingChat();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Transactional
  void createUser() {
    UserEntity req1 = UserEntity.builder()
        .email("thiennguyen9804@gmail.com")
        .password(bEncoder.encode("1"))
        .phoneNumber("000000000")
        .avatarUrl("https://i.pravatar.cc/300")
        .name("hayashing")
        .build();
    UserEntity req2 = UserEntity.builder()
        .email("kiana@gmail.com")
        .avatarUrl("https://i.pravatar.cc/300")
        .password(bEncoder.encode("1"))
        .phoneNumber("111111111")
        .name("kiana")
        .build();
    user1 = userRepository.save(req1);
    user2 = userRepository.save(req2);
    // log.info("🚀 ~ Seeding ~ voidcreateUser ~ user1:" + user1);
    // log.info("🚀 ~ Seeding ~ voidcreateUser ~ user2:" + user2);

  }

  @Transactional
  void addFriend() {
    UserDto t1, t2;
    t1 = userMapper.mapTo(user1);
    t2 = userMapper.mapTo(user2);
    // userService.requestFriend(t1, t2);
    // userService.acceptFriend(t2, t1);
    RelationshipEntity relationship = RelationshipEntity.builder()
        .id(RelationshipId.builder().user1(user1.id).user2(user2.id).build())
        .user1(user1)
        .user2(user2)
        .createdAt(Instant.now().minus(12, ChronoUnit.DAYS))
        .updatedAt(Instant.now().minus(11, ChronoUnit.DAYS))
        .relationship(Relationship.FRIEND)
        .build();
    relationshipRepository.save(relationship);
  }

  void setUpPost() {
    PostEntity postEntity1 = PostEntity.builder()
        .caption("my pretty Kiana")
        .imageUrl("https://res.cloudinary.com/dow4rkzmb/image/upload/v1752720466/yksl67l6w2wh1x4xuqwn.jpg")
        .user((user1))
        .createdAt(Instant.now().minus(10, ChronoUnit.DAYS))
        .build();

    PostEntity postEntity2 = PostEntity.builder()
        .caption("test image")
        .imageUrl("https://res.cloudinary.com/dow4rkzmb/image/upload/v1748053256/ylcte1lasx0hcrovhkhm.jpg")
        .user((user2))
        .createdAt(Instant.now().minus(10, ChronoUnit.DAYS))
        .build();
    post1 = postEntity1;
    post2 = postEntity2;

    posts.add(postEntity1);
    posts.add(postEntity2);
  }

  @Transactional
  void seedingChat() {
    MessageEntity message1 = MessageEntity.builder()
        .sender(user1)
        .receiver(user2)
        .text("Hello Kiana")
        .createdAt(Instant.now().minus(7, ChronoUnit.MINUTES))
        .build();

    MessageEntity message2 = MessageEntity.builder()
        .sender(user2)
        .receiver(user1)
        .text("Hello Hayashing, how it's going?")
        .createdAt(Instant.now().minus(5, ChronoUnit.MINUTES))
        .build();

    messageRepository.save(message1);
    messageRepository.save(message2);

  }

  @Transactional
  void seedingPost() {
    setUpPost();
    for (PostEntity post : posts) {
      postRepository.save(post);

      List<RelationshipEntity> results = relationshipRepository.findUsersByRelationship(post.user.id,
          Relationship.FRIEND);
      List<UserEntity> friendList = results.stream().map((RelationshipEntity rela) -> {
        if (rela.getUser1().id != post.user.id) {
          return rela.getUser1();
        } else {
          return rela.getUser2();

        }
      }).toList();

      List<PostVisibilityEntity> visibilities = friendList.stream()
          .map(user -> PostVisibilityEntity.builder()
              .id(new PostVisibilityId(user.id, post.id))
              .user(user)
              .post(post)
              .build())
          .collect(Collectors.toList());
      visibilities.add(PostVisibilityEntity.builder()
          .id(new PostVisibilityId(post.user.id, post.id))
          .user(post.user)
          .post(post)
          .build());

      postVisibilityRepository.saveAll(visibilities);
    }

  }

  @Transactional
  void seedInteraction() {
    List<String> emojis = List.of("😀", "😂", "😍", "🤔", "😎", "😭", "😡", "👍", "👎", "❤️");

    InteractionEntity interaction1 = InteractionEntity.builder()
        .user(user1)
        .post(post2)
        .emoji(emojis.get(2))
        .createdAt(Instant.now())
        .build();

    InteractionEntity interaction2 = InteractionEntity.builder()
        .user(user2)
        .post(post1)
        .emoji(emojis.get(9))
        .createdAt(Instant.now())
        .build();

    interactionRepository.save(interaction1);
    interactionRepository.save(interaction2);

    log.info("🚀 ~ Seeding ~ voidseedInteraction ~ user1 reacted to post2");
    log.info("🚀 ~ Seeding ~ voidseedInteraction ~ user2 reacted to post1");
  }

}
