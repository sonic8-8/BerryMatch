package com.gongcha.berrymatch.post.requestDTO;


import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
public class PostRequest {

    private String title;
    private String content;


    @Override
    public String toString() {
        return "PostRequest [title=" + title + ", content=" + content + "]";
    }

    @Builder
    public PostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
