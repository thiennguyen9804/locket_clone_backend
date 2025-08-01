package org.example.locket_clone_backend.domain.entity;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "posts")
@ToString
@Entity
public class PostEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "post_id_seq")
  public Long id;

  @NotEmpty
  public String imageUrl;
  public String caption;

  // @CurrentTimestamp
  public Instant createdAt;

  @JoinColumn(name = "user_id")
  @ManyToOne(cascade = CascadeType.MERGE)
  public UserEntity user;

  @OneToMany(mappedBy = "post", cascade = CascadeType.MERGE)
  public Set<InteractionEntity> interactionList;

}
