package org.example.locket_clone_backend.utils.specs;

import org.example.locket_clone_backend.domain.entity.PostEntity;
import org.example.locket_clone_backend.domain.entity.relationship_entity.Relationship;
import org.example.locket_clone_backend.domain.entity.relationship_entity.RelationshipEntity;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PostSpecs {
    public static Specification<PostEntity> isPostBelongToFriendOrSelf(Long userId) {
        return (Root<PostEntity> postRoot, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            // 1. Tạo JOIN với bảng RelationshipEntity
            Root<RelationshipEntity> relationshipRoot = query.from(RelationshipEntity.class);

            // 2. Điều kiện: Chỉ lấy quan hệ FRIEND
            Predicate isFriend = cb.equal(relationshipRoot.get("relationship"), Relationship.FRIEND);

            // 3. Điều kiện: userId phải có mặt trong quan hệ (user1 hoặc user2)
            Predicate userCondition = cb.or(
                cb.equal(relationshipRoot.get("id").get("user1"), userId),
                cb.equal(relationshipRoot.get("id").get("user2"), userId)
            );

            // 4. Điều kiện: updatedAt của quan hệ nhỏ hơn createdAt của bài post
            Predicate timeCondition = cb.lessThan(relationshipRoot.get("updatedAt"), postRoot.get("createdAt"));

            // 5. Điều kiện: Người đăng bài phải là user1 hoặc user2 trong quan hệ
            Predicate postOwnerCondition = cb.or(
                cb.equal(postRoot.get("user").get("id"), relationshipRoot.get("id").get("user1")),
                cb.equal(postRoot.get("user").get("id"), relationshipRoot.get("id").get("user2"))
            );

            // 6. Điều kiện: Người dùng tự đăng bài (để lấy bài của chính họ)
            Predicate selfPostCondition = cb.equal(postRoot.get("user").get("id"), userId);

            // 7. Kết hợp điều kiện: (Bài từ bạn bè) OR (Bài từ chính user)
            return cb.or(
                cb.and(isFriend, userCondition, timeCondition, postOwnerCondition),
                selfPostCondition
            );
        };
    }
}
