package com.gongcha.berrymatch.post;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostData {

    private Long postId;
    private String thumbnailUrl;
    private String fileUrl;
    private String title;
    private String createAt;



    @Builder
    private PostData(Long postId, String thumbnailUrl, String fileUrl, String title, String createAt) {
        this.postId = postId;
        this.thumbnailUrl = thumbnailUrl;
        this.fileUrl = fileUrl;
        this.title = title;
        this.createAt = createAt;
    }


}
