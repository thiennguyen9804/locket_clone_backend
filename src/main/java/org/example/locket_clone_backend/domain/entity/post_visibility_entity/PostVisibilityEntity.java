package org.example.locket_clone_backend.domain.entity.post_visibility_entity;

import java.time.Instant;

import org.example.locket_clone_backend.domain.entity.PostEntity;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post_visibilities")
@ToString
@Entity
@Setter
public class PostVisibilityEntity {
  @EmbeddedId
  private PostVisibilityId id;

  @ManyToOne(cascade = CascadeType.MERGE)
  @MapsId("post")
  @JoinColumn(name = "post", referencedColumnName = "id")
  private PostEntity post;

  @ManyToOne(cascade = CascadeType.MERGE)
  @MapsId("userCanSee")
  @JoinColumn(name = "user_can_see", referencedColumnName = "id")
  private UserEntity user;

  @CurrentTimestamp
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

}
