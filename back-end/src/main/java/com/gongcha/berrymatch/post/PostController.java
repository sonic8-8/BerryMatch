package com.gongcha.berrymatch.post;

import com.gongcha.berrymatch.ApiResponse;
import com.gongcha.berrymatch.post.requestDTO.PostRequest;
import com.gongcha.berrymatch.post.responseDTO.PostDataResponse;
import com.gongcha.berrymatch.post.responseDTO.PostResponse;
import com.gongcha.berrymatch.user.User;
import com.gongcha.berrymatch.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    /**
     * 게시글 업로드
     */
    @PostMapping("/post/upload")
    public ApiResponse<PostResponse> post(@RequestBody PostRequest request) {

        System.out.println("PostRequest값 : " + request.toString());

        return ApiResponse.ok(postService.postSave(request));
    }

    /**
     * 현재 접속한 페이지의 게시물들이랑 총 페이지 수를 보내줌.
     */
    @GetMapping("/postpage/{currentPage}")
    public ApiResponse<PostDataResponse> postList(@PathVariable int currentPage) {
        System.out.println("넘어온 페이지 값 : " + currentPage);
        return ApiResponse.ok(postService.getPosts(currentPage));
    }

}
