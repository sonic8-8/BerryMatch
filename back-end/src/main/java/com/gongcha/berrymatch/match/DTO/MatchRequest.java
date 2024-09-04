package com.gongcha.berrymatch.match.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class MatchRequest {

    //클라이언트 매칭요청 DTO
    private Long id;       // 유저 ID
    private String sport;  // 스포츠 종목
    private String time;   // 매칭 시간 (예: 14:00)
    private String groupCode;  // 그룹 코드
    private String date;   // 매칭 날짜 (예: 2024-08-23)
}