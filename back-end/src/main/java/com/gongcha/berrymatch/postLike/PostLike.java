package com.gongcha.berrymatch.postLike;

import com.gongcha.berrymatch.post.Post;
import com.gongcha.berrymatch.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @OneToOne
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
