package com.gongcha.berrymatch.post.requestDTO;


import com.gongcha.berrymatch.user.User;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
public class PostRequest {

    private Long id;
    private String title;
    private String content;


    @Override
    public String toString() {
        return "PostRequest [id" + id + "title=" + title + ", content=" + content + "]";
    }

    @Builder
    public PostRequest(Long id ,String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

}
