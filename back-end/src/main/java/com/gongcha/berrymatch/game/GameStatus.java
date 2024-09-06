package com.gongcha.berrymatch.game;

/**
 * 경기 진행 상태
 */
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameStatus {
    BEFORE("경기 전"),
    DURING("경기 중"),
    END("경기 끝 기록 등록 전"),
    OVER("경기 끝 기록 등록 완료");

    private final String text;
}
