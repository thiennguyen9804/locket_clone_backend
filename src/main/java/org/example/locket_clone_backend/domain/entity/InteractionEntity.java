package org.example.locket_clone_backend.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.Instant; 
import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "interactions")
@ToString
@Entity
public class InteractionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "interaction_id_seq")
    private Long id;

    @JoinColumn(name = "user_id")
	@ManyToOne(cascade = CascadeType.MERGE)
	private UserEntity user;

    @JoinColumn(name = "post_id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private PostEntity post;

    @NotEmpty
    private String emoji;

    private Instant createdAt;
}
