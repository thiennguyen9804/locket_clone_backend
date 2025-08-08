package org.example.locket_clone_backend.domain.entity.message_entity;

import java.io.Serializable;

import org.apache.logging.log4j.message.Message;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Data
@ToString

public class MessageId implements Serializable {
  private Long senderId;
  private Long receiverId;

  public MessageId() {
  }

  public MessageId(Long senderId, Long receiverId) {
    this.senderId = senderId;
    this.receiverId = receiverId;
  }
}
//
// @Embeddable
// public record MessageId(Long senderId, Long receiverId) {
// }
