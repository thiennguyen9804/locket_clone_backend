package org.example.locket_clone_backend.domain.dto;

import java.time.Instant;

import org.aspectj.asm.internal.Relationship;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.domain.entity.relationship_entity.RelationshipId;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
public class RelationshipDto {
    private RelationshipId id;

    private UserEntity user1;

    private UserEntity user2;

	private Relationship relationship;

	private Instant createdAt;
}
