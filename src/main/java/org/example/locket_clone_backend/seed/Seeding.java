package org.example.locket_clone_backend.seed;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.example.locket_clone_backend.domain.dto.UserDto;
import org.example.locket_clone_backend.domain.entity.PostEntity;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.mapper.Mapper;
import org.example.locket_clone_backend.repository.PostRepository;
import org.example.locket_clone_backend.repository.RelationshipRepository;
import org.example.locket_clone_backend.repository.UserRepository;
import org.example.locket_clone_backend.service.UserService;
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
	private UserEntity en1, en2;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final RelationshipRepository relationshipRepository;
	private final PasswordEncoder bEncoder;
	private final UserService userService;
	private final Mapper<UserEntity, UserDto> userMapper;
	private Instant now;

	List<PostEntity> posts = new ArrayList<>();

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			createUser();
			addFriend();
			// seedingPost();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	void addFriend() {
		UserDto t1, t2;
		t1 = userMapper.mapTo(user1);
		t2 = userMapper.mapTo(user2);
		userService.requestFriend(t1, t2);
		userService.acceptFriend(t2, t1);
		now = relationshipRepository.findByIds(t1.id, t2.id).get().getCreatedAt();
	}

	void setUpPost() {
		en1 = userRepository.findById(user1.id).get();
		en2 = userRepository.findById(user2.id).get();
		String imageUrl = "https://picsum.photos/seed/picsum/1000";
		for (int i = 1; i <= 5; i++) {
			PostEntity post = new PostEntity();
			post.imageUrl = imageUrl;
			post.caption = "Bài viết " + i + " của User 1";
			post.createdAt = Instant.now().plus(200, ChronoUnit.DAYS);
			post.user = user1;
			posts.add(post);
		}

		for (int i = 6; i <= 10; i++) {
			PostEntity post = new PostEntity();
			post.imageUrl = imageUrl;
			post.caption = "Bài viết " + i + " của User 2";
			post.createdAt = Instant.now().plus(100, ChronoUnit.DAYS);
			post.user = user2;
			posts.add(post);
		}
	}

	@Transactional
	void seedingPost() {
		setUpPost();
		for (PostEntity post : posts) {
			log.info("🚀 ~ Seeding ~ voidseedingPost ~ post: " + post);
			postRepository.save(post);
		}

	}

}
