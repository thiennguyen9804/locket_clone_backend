package org.example.locket_clone_backend.domain.dto;

import io.micrometer.common.lang.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SentMessageDto {
  private String text;
  @Nullable
  private String imageUrl;

  private Long receiverId;
}
