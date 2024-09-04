package com.gongcha.berrymatch.game;

import com.gongcha.berrymatch.match.domain.MatchUser;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class GameResultTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @Enumerated(EnumType.STRING)
    private GameRecordTempStatus gameRecordTempStatus;

    @OneToOne
    private MatchUser user;

    private int resultTeamA;

    private int resultTeamB;

    private int votes;

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
