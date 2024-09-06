package com.gongcha.berrymatch.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserMatchStatus {


    MATCHED("매칭 중"),
    NOT_MATCHED("매칭 중이 아님"),
    MATCH_COMPLETED("매칭 완료");

    private final String text;
}
