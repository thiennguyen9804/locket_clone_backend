package org.example.locket_clone_backend.repository;

import org.example.locket_clone_backend.domain.entity.message_entity.MessageEntity;
import org.example.locket_clone_backend.domain.entity.message_entity.MessageId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, MessageId> {

}
