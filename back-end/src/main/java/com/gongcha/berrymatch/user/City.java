package com.gongcha.berrymatch.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum City {

    SEOUL("서울"),
    BUSAN("부산"),
    DAEGU("대구"),
    INCHEON("인천"),
    GWANGJU("광주"),
    DAEJEON("대전"),
    ULSAN("울산"),
    SEJONG("세종");

    private final String text;

}
