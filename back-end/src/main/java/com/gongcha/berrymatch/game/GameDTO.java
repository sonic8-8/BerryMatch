package com.gongcha.berrymatch.game;

import com.gongcha.berrymatch.match.domain.Match;
import com.gongcha.berrymatch.match.domain.MatchUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameDTO {

    private Match match;

    private Game game;

    private MatchUser user;

    private int resultTeamA;

    private int resultTeamB;

    private GameRecordTempStatus gameRecordTempStatus;

    private int votes;

    @Builder
    public GameDTO(Game game,  int resultTeamA, int resultTeamB){
        this.game = game;
        this.resultTeamA = resultTeamA;
        this.resultTeamB = resultTeamB;
    }

    @Builder
    public GameDTO(Game game, MatchUser user, int resultTeamA, int resultTeamB){
        this.game = game;
        this.user = user;
        this.resultTeamA = resultTeamA;
        this.resultTeamB = resultTeamB;
    }

    @Builder
    public GameDTO(Game game, MatchUser user, int resultTeamA, int resultTeamB, GameRecordTempStatus gameRecordTempStatus){
        this.game = game;
        this.user = user;
        this.resultTeamA = resultTeamA;
        this.resultTeamB = resultTeamB;
        this.gameRecordTempStatus = gameRecordTempStatus;
    }

    public void setResultTeamA(int resultTeamA) {
        this.resultTeamA = resultTeamA;
    }

    public void setResultTeamB(int resultTeamB) {
        this.resultTeamB = resultTeamB;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    //    @Builder
//    public GameDTO(Long gameId, int resultTeamA, int resultTeamB, int votes){
//        this.gameId = gameId;
//        this.resultTeamA = resultTeamA;
//        this.resultTeamB = resultTeamB;
//        this.votes = votes;
//    }
}
