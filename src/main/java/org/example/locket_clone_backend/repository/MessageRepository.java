package org.example.locket_clone_backend.repository;

import org.example.locket_clone_backend.domain.entity.message_entity.MessageEntity;
import org.example.locket_clone_backend.domain.entity.message_entity.MessageId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface MessageRepository extends JpaRepository<MessageEntity, MessageId> {
  @NativeQuery(value = "SELECT * FROM MESSAGES WHERE (sender = ?1 AND receiver = ?2) OR (sender = ?2 AND receiver = ?1) ORDER BY created_at DESC", countQuery = "SELECT count(*) FROM MEESAGES WHERE (sender = ?1 AND receiver = ?2) OR (sender = ?2 AND receiver = ?1)")
  Page<MessageEntity> findMessagesBetweenUsers(
      Long user1,
      Long user2,
      Pageable pageable);
}
