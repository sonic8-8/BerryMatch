package com.gongcha.berrymatch.game;

import com.gongcha.berrymatch.match.domain.Match;
import com.gongcha.berrymatch.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    private int resultTeamA;

    private int resultTeamB;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "game_id")
    private Match match;
}
