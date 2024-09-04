package com.gongcha.berrymatch.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameStatus {

    TEST("테스트용");

    private final String text;
}
