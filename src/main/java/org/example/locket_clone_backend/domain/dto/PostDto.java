package org.example.locket_clone_backend.domain.dto;

import java.time.Instant;

import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
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
}