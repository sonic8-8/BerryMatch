package com.gongcha.berrymatch.match.DTO;

import lombok.Data;

@Data
public class MatchReady {

    //준비신호DTO
    private Long id;
    private String matchReadyStatus;
    private String message;
}
