package com.gongcha.berrymatch.postLike.requestDTO;

import com.gongcha.berrymatch.post.Post;
import com.gongcha.berrymatch.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Getter
@NoArgsConstructor
public class PostLikeRequest {

    private int postId;
    private int userId;

    @Builder
    public PostLikeRequest(int postId, int userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public Long toLong(int value) {
        return Long.valueOf(value);
    }

    public PostLikeServiceRequest toServiceRequest() {
        return PostLikeServiceRequest.builder()
                .userId(toLong(postId))
                .postId(toLong(userId))
                .build();
    }

}
