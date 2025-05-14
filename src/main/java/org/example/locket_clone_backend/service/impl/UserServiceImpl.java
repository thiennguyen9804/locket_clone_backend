package org.example.locket_clone_backend.service.impl;

import org.example.locket_clone_backend.domain.dto.UserDto;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.domain.entity.relationship_entity.Relationship;
import org.example.locket_clone_backend.domain.entity.relationship_entity.RelationshipEntity;
import org.example.locket_clone_backend.domain.entity.relationship_entity.RelationshipId;
import org.example.locket_clone_backend.mapper.Mapper;
import org.example.locket_clone_backend.repository.RelationshipRepository;
import org.example.locket_clone_backend.repository.UserRepository;
import org.example.locket_clone_backend.security.JwtUtilImpl;
import org.example.locket_clone_backend.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@Log
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final RelationshipRepository relationshipRepository;
	private final Mapper<UserEntity, UserDto> userMapper;
	private UserEntity en1, en2;

	@Override
	public void requestFriend(UserDto from, UserDto to) {
		log.info("🚀 ~ UserServiceImpl ~ voidrequestFriend ~ from: " + from);
		log.info("🚀 ~ UserServiceImpl ~ voidrequestFriend ~ to: " + to);
		en1 = userRepository.findById(from.id).get();
		en2 = userRepository.findById(to.id).get();
		RelationshipEntity relationshipEntity = RelationshipEntity.builder()
				.id(new RelationshipId(from.id, to.id))
				.relationship(Relationship.PENDING)
				.user1(en1)
				.user2(en2)
				.build();
		
		log.info("🚀 ~ UserServiceImpl ~ voidrequestFriend ~ relationshipEntity: " + relationshipEntity);
		RelationshipEntity saved = relationshipRepository.save(relationshipEntity);
		log.info("🚀 ~ UserServiceImpl ~ voidrequestFriend ~ saved: " + saved);

	}

	@Override
	public void acceptFriend(UserDto from, UserDto to) {
		RelationshipEntity res = relationshipRepository
				.findByIds(from.id, to.id)
				.orElseThrow(() -> new RuntimeException("No such relationship :((("));

		res.setRelationship(Relationship.FRIEND);

		relationshipRepository.save(res);
	}

}
