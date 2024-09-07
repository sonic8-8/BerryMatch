package com.gongcha.berrymatch.postLike;

import com.gongcha.berrymatch.post.Post;
import com.gongcha.berrymatch.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserIdAndPostId(Long postId, Long userId);
    int deleteByUserIdAndPostId(Long user_id, Long post_id);
}
