package org.example.locket_clone_backend.repository;

import org.example.locket_clone_backend.domain.entity.message_entity.MessageEntity;
import org.example.locket_clone_backend.domain.entity.message_entity.MessageId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface MessageRepository extends JpaRepository<MessageEntity, MessageId> {
  @NativeQuery(value = "SELECT * FROM MESSAGES WHERE (senderId = ?1 AND receiverId = ?2) OR (senderId = ?2 AND receiverId = ?1) ORDER BY created_at DESC", countQuery = "SELECT count(*) FROM MEESAGES WHERE (senderId = ?1 AND receiverId = ?2) OR (senderId = ?2 AND receiverId = ?1)")
  Page<MessageEntity> findMessagesBetweenUsers(
      Long user1,
      Long user2,
      Pageable pageable);
}
