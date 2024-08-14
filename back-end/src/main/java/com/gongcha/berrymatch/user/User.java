package com.gongcha.berrymatch.user;

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
    private List<UserActivity> activities;

}
