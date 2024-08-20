package com.gongcha.berrymatch.game;

import com.gongcha.berrymatch.match.Match;
import com.gongcha.berrymatch.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<User> users;

    private GameStatus gameStatus;

    private String gameResult;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne
    private Match match;
}
