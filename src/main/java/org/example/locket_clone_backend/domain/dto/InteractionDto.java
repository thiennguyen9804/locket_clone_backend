package org.example.locket_clone_backend.domain.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InteractionDto {
  private Long id;
  private UserDto user;
  private Long postId;
  private String emoji;
  private Instant createdAt;
  private PostDto post;
}
