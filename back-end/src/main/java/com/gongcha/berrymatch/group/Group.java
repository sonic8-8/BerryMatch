package com.gongcha.berrymatch.group;

import com.gongcha.berrymatch.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int code;

    @OneToMany
    private List<User> users;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
