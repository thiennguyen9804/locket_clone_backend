package org.example.locket_clone_backend.domain.entity.message_entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
public class MessageId {
  private Long senderId;
  private Long receiverId;
}
