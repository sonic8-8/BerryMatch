package com.gongcha.berrymatch.match;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MatchStatus {

    PENDING("대기중"),
    ACTIVE("진행중"),
    COMPLETED("완료"),
    CANCELLED("취소");

    private final String text;
}
