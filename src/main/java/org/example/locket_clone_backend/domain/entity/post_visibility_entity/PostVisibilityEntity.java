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
@Getter
public class PostVisibilityEntity {
  @EmbeddedId
  private PostVisibilityId id;

  @ManyToOne
  @MapsId("post")
  @JoinColumn(name = "post", referencedColumnName = "id")
  private PostEntity post;

  @ManyToOne
  @MapsId("userCanSee")
  @JoinColumn(name = "user_can_see", referencedColumnName = "id")
  private UserEntity user;

}
