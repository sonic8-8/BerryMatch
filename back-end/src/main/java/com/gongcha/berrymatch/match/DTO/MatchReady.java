package com.gongcha.berrymatch.match.DTO;

import lombok.Data;

@Data
public class MatchReady {

    private Long id;
    private String userNickname;
    private String matchReadyStatus;
    private boolean allUsersReady;

    // 필요하면 추가 가능: 매치에 대한 필수 정보만 포함
    // private Long matchId;

    public MatchReady(Long id, String userNickname, String matchReadyStatus, boolean allUsersReady) {
        this.id = id;
        this.userNickname = userNickname;
        this.matchReadyStatus = matchReadyStatus;
        this.allUsersReady = allUsersReady;
    }


}
