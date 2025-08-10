package org.example.locket_clone_backend.domain.entity.message_entity;

import java.sql.Timestamp;
import java.time.Instant;

import org.example.locket_clone_backend.domain.entity.PostEntity;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Table(name = "messages")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_seq")
  private Long id;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "sender", referencedColumnName = "id")
  private UserEntity sender;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "receiver", referencedColumnName = "id")
  private UserEntity receiver;

  private String text;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "post_id", referencedColumnName = "id")
  private PostEntity post;

  @CurrentTimestamp
  private Instant createdAt;
}
