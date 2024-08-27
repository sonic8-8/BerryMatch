package com.gongcha.berrymatch.group;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GroupStatus {

    TEST("테스트용");

    private final String text;
}
