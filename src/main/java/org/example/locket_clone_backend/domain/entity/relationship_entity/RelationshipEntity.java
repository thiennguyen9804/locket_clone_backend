package org.example.locket_clone_backend.domain.entity.relationship_entity;

import java.time.Instant;

import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "relationships")
@ToString
@Entity
@Setter
public class RelationshipEntity {
  @EmbeddedId
  private RelationshipId id;

  // sender
  @OneToOne
  @MapsId("user1")
  @JoinColumn(name = "user1", referencedColumnName = "id")
  private UserEntity user1;

  // receiver
  @OneToOne
  @MapsId("user2")
  @JoinColumn(name = "user2", referencedColumnName = "id")
  private UserEntity user2;

  @Enumerated(EnumType.STRING)
  @NotBlank
  private Relationship relationship;

  // @CurrentTimestamp
  private Instant createdAt;

  // @UpdateTimestamp
  private Instant updatedAt;
}
