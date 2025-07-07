package org.example.locket_clone_backend.repository;

import org.example.locket_clone_backend.domain.entity.InteractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface InteractionRepository extends JpaRepository<InteractionEntity, Long> {

}
