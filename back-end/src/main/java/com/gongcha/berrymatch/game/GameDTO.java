package com.gongcha.berrymatch.game;

import com.gongcha.berrymatch.match.domain.Match;
import com.gongcha.berrymatch.match.domain.MatchUser;
import com.gongcha.berrymatch.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameDTO {

    private Game game;

    private User user;

    private int resultTeamA;

    private int resultTeamB;

    private GameRecordTempStatus gameRecordTempStatus;

    private int votes;

    @Builder
    public GameDTO(Game game, User user, int resultTeamA, int resultTeamB, GameRecordTempStatus gameRecordTempStatus,int votes){
        this.game = game;
        this.user = user;
        this.resultTeamA = resultTeamA;
        this.resultTeamB = resultTeamB;
        this.gameRecordTempStatus = gameRecordTempStatus;
        this.votes = votes;
    }

//    public void setResultTeamA(int resultTeamA) {
//        this.resultTeamA = resultTeamA;
//    }
//
//    public void setResultTeamB(int resultTeamB) {
//        this.resultTeamB = resultTeamB;
//    }
//
//    public void setVotes(int votes) {
//        this.votes = votes;
//    }

}
