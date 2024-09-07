package com.gongcha.berrymatch.post;

import com.gongcha.berrymatch.ApiResponse;
import com.gongcha.berrymatch.exception.BusinessException;
import com.gongcha.berrymatch.exception.ErrorCode;
import com.gongcha.berrymatch.post.requestDTO.MyPostRequest;
import com.gongcha.berrymatch.post.requestDTO.PostRequest;
import com.gongcha.berrymatch.post.responseDTO.PostDataResponse;
import com.gongcha.berrymatch.post.responseDTO.PostResponse;
import com.gongcha.berrymatch.postFile.PostFile;
import com.gongcha.berrymatch.postFile.PostFileRepository;
import com.gongcha.berrymatch.user.User;
import com.gongcha.berrymatch.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.gongcha.berrymatch.exception.ErrorCode.MEMBER_NOT_FOUND;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostFileRepository postFileRepository;
    @Autowired
    private UserRepository userRepository;


    // 게시글 업로드
    public PostResponse postSave(PostRequest request) {

        // User 타입이 필요하니깐, user_id값만 갖고 있고 나머지는 null인 User객체를 만들어준다.

        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        Post post = Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        System.out.println("아이디 까봄 : " + user.getId());
        System.out.println("유저의 아이디는 ? " + user.getIdentifier());

        postRepository.save(post);

        return PostResponse.of(post);

    }

    // 페이징
    public PostDataResponse getPosts(int currentPage) {

        Page<Post> posts = postRepository.findPageBy(PageRequest.of(currentPage - 1, 6));

        List<PostData> postDataList = new ArrayList<>();

        for (Post post : posts.getContent()) {
            Long postId = post.getId();
            String thumbnailUrl = postFileRepository.findByPostId(post.getId()).
                    orElseThrow(() -> new BusinessException(ErrorCode.FILE_NOT_EXIST)).getThumbFileUrl();
            String fileUrl = postFileRepository.findByPostId(post.getId()).
                    orElseThrow(() -> new BusinessException(ErrorCode.FILE_NOT_EXIST)).getFileUrl();
            String nickname = post.getUser().getNickname();
            String title = post.getTitle();
            String content = post.getContent();
            String createAt = post.getCreatedAt().toString().substring(0, 10);

            postDataList.add(PostData.builder()
                    .postId(postId)
                    .thumbnailUrl(thumbnailUrl)
                    .fileUrl(fileUrl)
                    .title(title)
                    .content(content)
                    .nickname(nickname)
                    .createAt(createAt)
                    .build());
        }

        String totalPages = String.valueOf(posts.getTotalPages());

        System.out.println("postDataList의 값 : " + postDataList);

        return PostDataResponse.builder()
                .postDataList(postDataList)
                .totalPages(totalPages)
                .build();
    }

    // 나의 게시물
    public PostDataResponse getMyPosts(MyPostRequest request, int currentPage) {

        // 나의 게시물 누른놈
        Page<Post> posts = postRepository.findPageByUser(request.getId(), PageRequest.of(currentPage - 1, 6));

        List<PostData> postDataList = new ArrayList<>();

        for (Post post : posts.getContent()) {
            Long postId = post.getId();
            String thumbnailUrl = postFileRepository.findByPostId(post.getId()).
                    orElseThrow(() -> new BusinessException(ErrorCode.FILE_NOT_EXIST)).getThumbFileUrl();
            String fileUrl = postFileRepository.findByPostId(post.getId()).
                    orElseThrow(() -> new BusinessException(ErrorCode.FILE_NOT_EXIST)).getFileUrl();
            String nickname = post.getUser().getNickname();
            String title = post.getTitle();
            String content = post.getContent();
            String createAt = post.getCreatedAt().toString().substring(0, 10);



            postDataList.add(PostData.builder()
                    .postId(postId)
                    .thumbnailUrl(thumbnailUrl)
                    .fileUrl(fileUrl)
                    .title(title)
                    .content(content)
                    .nickname(nickname)
                    .createAt(createAt)
                    .build());
        }

        String totalPages = String.valueOf(posts.getTotalPages());

        System.out.println("postDataList의 값 : " + postDataList);

        return PostDataResponse.builder()
                .postDataList(postDataList)
                .totalPages(totalPages)
                .build();
    }

}
