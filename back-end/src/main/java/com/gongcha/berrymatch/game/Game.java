package com.gongcha.berrymatch.game;

import com.gongcha.berrymatch.match.domain.Match;
import com.gongcha.berrymatch.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    private String gameTitle;

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

    @Builder
    public Game(String gameTitle, List<User> users){
        this.gameTitle = gameTitle;
        this.users = users;
    }

    public void setResultTeamA(int resultTeamA) {
        this.resultTeamA = resultTeamA;
    }

    public void setResultTeamB(int resultTeamB) {
        this.resultTeamB = resultTeamB;
    }
}
