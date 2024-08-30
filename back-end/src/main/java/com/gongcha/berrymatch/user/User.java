package com.gongcha.berrymatch.user;

import com.gongcha.berrymatch.chatMessage.ChatMessage;
import com.gongcha.berrymatch.chatRoom.ChatRoom;
import com.gongcha.berrymatch.game.Game;
import com.gongcha.berrymatch.group.Group;
import com.gongcha.berrymatch.match.Match;
import com.gongcha.berrymatch.post.Post;
import com.gongcha.berrymatch.postLike.PostLike;
import com.gongcha.berrymatch.springSecurity.constants.ProviderInfo;
import com.gongcha.berrymatch.user.RequestDTO.UserSignupServiceRequest;
import com.gongcha.berrymatch.userActivity.UserActivity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String identifier;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private City city;

    @Enumerated(EnumType.STRING)
    private District district;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int age;

    private String phoneNumber;

    @Column(name = "profile_image_url", length = 512)
    private String profileImageUrl;

    private String introduction;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private ProviderInfo providerInfo;

    @Enumerated(EnumType.STRING)
    private UserMatchStatus userMatchStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserActivity> userActivities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matches_id")
    private Match match;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "chat_message_id")
    private ChatMessage chatMessage;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_like_id")
    private PostLike postLike;

    @Builder
    public User(String identifier, String nickname, City city, District district, Gender gender, int age, String phoneNumber, String profileImageUrl, String introduction, String email, Role role, LocalDateTime createdAt, ProviderInfo providerInfo, UserMatchStatus userMatchStatus) {
        this.identifier = identifier;
        this.nickname = nickname;
        this.city = city;
        this.district = district;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
        this.role = role;
        this.createdAt = createdAt;
        this.providerInfo = providerInfo;
        this.userMatchStatus = userMatchStatus;
    }

    public boolean isRegistered() {
        return this.role != Role.NOT_REGISTERED;
    }

    /**
     * 회원가입 비즈니스 로직에 사용됨. 회원정보를 업데이트해주는 메서드
     */
    public void update(UserSignupServiceRequest request) {
        this.nickname = request.getNickname();
        this.city = request.getCity();
        this.district = request.getDistrict();
        this.gender = request.getGender();
        this.age = request.getAge();
        this.phoneNumber = request.getPhoneNumber();
        this.profileImageUrl = request.getProfileImageUrl();
        this.introduction = request.getIntroduction();
        this.role = Role.USER;
    }
}
