package org.example.locket_clone_backend.repository;

import java.util.List;

import org.example.locket_clone_backend.domain.dto.MessageResponse;
import org.example.locket_clone_backend.domain.entity.message_entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
  @Query(value = "SELECT * FROM messages WHERE (sender = ?1 AND receiver = ?2) OR (sender = ?2 AND receiver = ?1) ORDER BY created_at DESC", countQuery = "SELECT count(*) FROM messages WHERE (sender = ?1 AND receiver = ?2) OR (sender = ?2 AND receiver = ?1)", nativeQuery = true)
  Page<MessageEntity> findMessagesBetweenUsers(
      Long user1,
      Long user2,
      Pageable pageable);

  @Query(value = """
          SELECT m.* FROM messages m
          WHERE m.created_at = (
              SELECT MAX(m2.created_at)
              FROM messages m2
              WHERE (m2.sender = :userId OR m2.receiver = :userId)
          )
      """, nativeQuery = true)
  List<MessageEntity> findLatestMessageByUser(Long userId);

}
