package com.gongcha.berrymatch.post;

import com.gongcha.berrymatch.ApiResponse;
import com.gongcha.berrymatch.post.requestDTO.PostRequest;
import com.gongcha.berrymatch.post.responseDTO.PostDataResponse;
import com.gongcha.berrymatch.post.responseDTO.PostResponse;
import com.gongcha.berrymatch.postFile.PostFile;
import com.gongcha.berrymatch.postFile.PostFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostFileRepository postFileRepository;


    // 게시글 업로드
    public PostResponse postSave(PostRequest request) {

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        postRepository.save(post);

        return PostResponse.of(post);
    }

    // 페이징
    public PostDataResponse getPosts(int currentPage) {
        Page<Post> posts = postRepository.findPageBy(PageRequest.of(currentPage - 1, 6));

        List<PostData> postDataList = new ArrayList<>();

        for (Post post : posts.getContent()) {
            Long postId = post.getId();
            String thumbnailUrl = postFileRepository.findByPostId(post.getId()).get().getThumbFileUrl();
            String fileUrl = postFileRepository.findByPostId(post.getId()).get().getFileUrl();
            String title = post.getTitle();
            String createAt = post.getCreatedAt().toString();


            postDataList.add(PostData.builder()
                    .postId(postId)
                    .thumbnailUrl(thumbnailUrl)
                    .fileUrl(fileUrl)
                    .title(title)
                    .createAt(createAt)
                    .build());
        }

        String totalPages = String.valueOf(posts.getTotalPages());

        return PostDataResponse.builder()
                .postDataList(postDataList)
                .totalPages(totalPages)
                .build();
    }




}
