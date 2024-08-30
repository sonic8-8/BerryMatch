package com.gongcha.berrymatch.match;

import com.gongcha.berrymatch.game.Game;
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
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matches_id")
    private Long id;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "game_id")
    private Game game;
}
