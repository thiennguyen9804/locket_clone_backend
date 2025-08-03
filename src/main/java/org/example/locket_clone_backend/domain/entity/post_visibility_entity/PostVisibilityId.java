package org.example.locket_clone_backend.domain.entity.post_visibility_entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Log
@Builder
public class PostVisibilityId {
  private Long userCanSee;
  private Long post;
}
