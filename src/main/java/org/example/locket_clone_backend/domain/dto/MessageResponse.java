package org.example.locket_clone_backend.domain.dto;

import java.security.Timestamp;

import org.example.locket_clone_backend.domain.entity.UserEntity;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {
  private String text;
  @Nullable
  private String imageUrl;
  private UserDto sender;
  private UserDto receiver;
  private Timestamp createdAt;

}
