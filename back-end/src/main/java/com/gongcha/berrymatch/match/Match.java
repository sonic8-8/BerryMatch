package com.gongcha.berrymatch.match;

import com.gongcha.berrymatch.game.Game;
import com.gongcha.berrymatch.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<User> users;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne
    private Game game;
}
