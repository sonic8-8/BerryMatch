package com.gongcha.berrymatch.postLike;

import com.gongcha.berrymatch.ApiResponse;
import com.gongcha.berrymatch.post.Post;
import com.gongcha.berrymatch.postLike.requestDTO.PostLikeRequest;
import com.gongcha.berrymatch.postLike.responseDTO.PostLikeResponse;
import com.gongcha.berrymatch.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/postlike")
@CrossOrigin(origins = "http://localhost:3000")
public class PostLikeController {

    @Autowired
    private PostLikeService postLikeService;


    @GetMapping("/check")
    public ApiResponse<Boolean> checkPostLike(@RequestParam("postId") Long postId,
                                              @RequestParam("userId") Long userId) {

        // Post 자료형으로 받았을 때, Post의 id에 자동으로 매핑을 해줘?
        System.out.println("해당 게시물의 좋아요 여부 확인 중..." + postId + "  ^-^?  " + userId);

        return ApiResponse.ok(postLikeService.checkLike(postId, userId));
    }


    @PostMapping("/update")
    public ApiResponse<PostLikeResponse> postLike(@RequestBody PostLikeRequest request) {

        // 좋아요 누른 놈의 정체
        System.out.println("좋아요 누른 게시물 : " + request.getPostId());
        System.out.println("좋아요 누른놈 : " + request.getUserId());
        // 만약 이미 있는 경우라면, post_like 테이블에 있는 해당 유저의 데이터 삭제

        return ApiResponse.ok(postLikeService.updateLike(request.toServiceRequest()));
    }

}
