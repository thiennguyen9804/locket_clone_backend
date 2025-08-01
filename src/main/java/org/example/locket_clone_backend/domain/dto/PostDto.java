package org.example.locket_clone_backend.domain.dto;

import java.time.Instant;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PostDto {
  public Long id;
  public String imageUrl;
  public String caption;
  public Instant createdAt;
  public UserDto user;
  public Set<InteractionDto> interactionList;
}
