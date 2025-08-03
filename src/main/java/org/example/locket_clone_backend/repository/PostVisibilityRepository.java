package org.example.locket_clone_backend.repository;

import org.example.locket_clone_backend.domain.entity.post_visibility_entity.PostVisibilityEntity;
import org.example.locket_clone_backend.domain.entity.post_visibility_entity.PostVisibilityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostVisibilityRepository extends JpaRepository<PostVisibilityEntity, PostVisibilityId> {

}
