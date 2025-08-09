package org.example.locket_clone_backend.domain.dto;

import java.security.Timestamp;
import java.time.Instant;

import org.example.locket_clone_backend.domain.entity.UserEntity;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class MessageResponse {
  private String text;
  @Nullable
  private String imageUrl;
  private UserDto sender;
  private UserDto receiver;
  private Instant createdAt;

}
