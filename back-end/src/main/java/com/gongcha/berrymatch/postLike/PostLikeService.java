package com.gongcha.berrymatch.postLike;

import com.gongcha.berrymatch.post.Post;
import com.gongcha.berrymatch.post.PostRepository;
import com.gongcha.berrymatch.postLike.requestDTO.PostLikeRequest;
import com.gongcha.berrymatch.postLike.requestDTO.PostLikeServiceRequest;
import com.gongcha.berrymatch.postLike.responseDTO.PostLikeResponse;
import com.gongcha.berrymatch.user.User;
import com.gongcha.berrymatch.user.UserRepository;
import com.gongcha.berrymatch.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostLikeService {

    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public boolean checkLike(Long postId, Long userId) {



        boolean result = postLikeRepository.existsByUserIdAndPostId(userId, postId);

        return result;
    }

    @Transactional
    public PostLikeResponse updateLike(PostLikeServiceRequest request) {


        // 1. 조회했는데 반환값이 없으면 save, 있으면 delete 해버려
        // 왜 PostLike 타입으로 반환받아야댐? -> 조회를 하면 각 컬럼에 맞는 변수명들을 엔터티인 PostLike가 갖고있잖아
        boolean result = postLikeRepository.existsByUserIdAndPostId(request.getUserId(), request.getPostId());

        System.out.println("DB에서 좋아요 여부 조회 : " + result);

        Post post = postRepository.findById(request.getPostId()).orElse(null);
        User user = userRepository.findById(request.getUserId()).orElse(null);

        System.out.println("PostRepository : " + post.getId());
        System.out.println("UserRepository : " + user.getId());


        PostLike postLike = PostLike.builder()
                .post(post)
                .user(user)
                .build();

        System.out.println("PostLike 빌더의 게시글 id 값 : " + postLike.getPost().getId());
        System.out.println("PostLike 빌더의 유저 id 값 : " + postLike.getUser().getId());

        if(!result){
            PostLike saveResult = postLikeRepository.save(postLike);

            System.out.println("저장된 후 결과값 : " + saveResult);
        }else{
            int deleteResult = postLikeRepository.deleteByUserIdAndPostId(request.getUserId(), request.getPostId());

            System.out.println("삭제된 후 결과값 : " + deleteResult);
        }


        return null;
    }

}
