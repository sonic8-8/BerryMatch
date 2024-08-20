package com.gongcha.berrymatch.post;

import com.gongcha.berrymatch.postFile.PostFile;
import com.gongcha.berrymatch.postLike.PostLike;
import com.gongcha.berrymatch.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToOne
    private User user;

    @OneToMany
    private List<PostFile> postFiles;

    @OneToMany
    private List<PostLike> postLikes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private int view;
}
