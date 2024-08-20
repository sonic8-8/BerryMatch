package com.gongcha.berrymatch.user;

import com.gongcha.berrymatch.chatMessage.ChatMessage;
import com.gongcha.berrymatch.chatRoom.ChatRoom;
import com.gongcha.berrymatch.game.Game;
import com.gongcha.berrymatch.group.Group;
import com.gongcha.berrymatch.post.Post;
import com.gongcha.berrymatch.postLike.PostLike;
import com.gongcha.berrymatch.userActivity.UserActivity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String nickname;

    private String password;

    @Enumerated(EnumType.STRING)
    private City city;

    @Enumerated(EnumType.STRING)
    private District district;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int age;

    private String phoneNumber;

    private String profileImageUrl;

    private String introduction;

    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany
    private List<UserActivity> userActivities;

    @ManyToOne
    private Group group;

    @ManyToOne
    private Game game;

    @ManyToOne
    private ChatRoom chatRoom;

    @OneToOne
    private ChatMessage chatMessage;

    @OneToOne
    private Post post;

    @OneToOne
    private PostLike postLike;

}
