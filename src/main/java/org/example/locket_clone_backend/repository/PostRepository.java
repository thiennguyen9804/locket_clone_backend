package org.example.locket_clone_backend.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import org.example.locket_clone_backend.domain.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lombok.extern.java.Log;

public interface PostRepository extends JpaRepository<PostEntity, Long>, JpaSpecificationExecutor<PostEntity> {
    @Query(value = """
                SELECT * FROM posts p
                WHERE
                    (cast(:cursorCreatedAt as date) IS NULL OR p.created_at < :cursorCreatedAt)
                    AND p.user_id IN (
                        SELECT CASE
                                   WHEN r.user1 = :userId THEN r.user2
                                   ELSE r.user1
                               END
                        FROM relationships r
                        WHERE (r.user1 = :userId OR r.user2 = :userId)
                          AND r.relationship = 'FRIEND'
                          AND r.updated_at < p.created_at
                        UNION
                        SELECT :userId
                    )
                ORDER BY p.created_at DESC
                LIMIT :limit
            """, nativeQuery = true)
    List<PostEntity> findPostsKeyset(
            @Param("userId") Long userId,
            @Param("cursorCreatedAt") OffsetDateTime cursorCreatedAt,
            @Param("limit") int limit);

    @Query(value = """
                SELECT COUNT(*) FROM posts p
                WHERE
                    p.user_id IN (
                        SELECT CASE
                                   WHEN r.user1 = :userId THEN r.user2
                                   ELSE r.user1
                               END
                        FROM relationships r
                        WHERE (r.user1 = :userId OR r.user2 = :userId)
                          AND r.relationship = 'FRIEND'
                          AND r.updated_at < p.created_at
                        UNION
                        SELECT :userId
                    )
            """, nativeQuery = true)
    Long countAllPosts(
            @Param("userId") Long userId    
    );

}
