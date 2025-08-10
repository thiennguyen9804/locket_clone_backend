package org.example.locket_clone_backend.repository;

import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.domain.entity.relationship_entity.Relationship;
import org.example.locket_clone_backend.domain.entity.relationship_entity.RelationshipEntity;
import org.example.locket_clone_backend.domain.entity.relationship_entity.RelationshipId;
import org.hibernate.annotations.processing.HQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<RelationshipEntity, RelationshipId> {
  // get 'friend' relationship between user1 and user2
  @Query("""
      	SELECT r FROM RelationshipEntity r
      	WHERE (r.user1.id = ?1 AND r.user2.id = ?2)
      		OR (r.user1.id = ?2 AND r.user2.id = ?1)
      """)
  Optional<RelationshipEntity> findByIds(@NonNull Long user1, @NonNull Long user2);

  // use userId and a relationship R, get other users that have relationship R
  // with user
  @Query("""
      	SELECT r FROM RelationshipEntity r
      	WHERE (r.user1.id = ?1 OR r.user2.id = ?1)
      		AND r.relationship = ?2
      """)
  List<RelationshipEntity> findUsersByRelationship(Long userId, Relationship relationship);

  @Query("""
      	SELECT r FROM RelationshipEntity r
      	WHERE (r.user1.id = ?1)
      		AND r.relationship = ?2
      """)
  List<RelationshipEntity> findPendingFriendByUser1(Long userId, Relationship relationship);

  @Query("""
      	SELECT r FROM RelationshipEntity r
      	WHERE (r.user2.id = ?1)
      		AND r.relationship = ?2
      """)
  List<RelationshipEntity> getPendingFriendByUser2(Long userId, Relationship relationship);

}
