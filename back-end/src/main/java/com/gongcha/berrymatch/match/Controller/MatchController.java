package com.gongcha.berrymatch.match.Controller;

import com.gongcha.berrymatch.ApiResponse;
import com.gongcha.berrymatch.match.DTO.MatchCancelRequest;
import com.gongcha.berrymatch.match.DTO.MatchReady;
import com.gongcha.berrymatch.match.DTO.MatchRequest;
import com.gongcha.berrymatch.match.DTO.MatchResponse;
import com.gongcha.berrymatch.match.Service.MatchCancelService;
import com.gongcha.berrymatch.match.Service.MatchReadyService;
import com.gongcha.berrymatch.match.Service.MatchRequestProcessingService;
import com.gongcha.berrymatch.match.ThreadLocal.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class MatchController {

    private final MatchRequestProcessingService matchRequestProcessingService;
    private final MatchCancelService matchCancelService;
    private final MatchReadyService matchReadyService;


    @Autowired
    public MatchController(MatchRequestProcessingService matchRequestProcessingService, MatchCancelService cancelMatching, MatchCancelService matchCancelService, MatchReadyService matchReadyService
    ) {
        this.matchRequestProcessingService = matchRequestProcessingService;
        this.matchCancelService = matchCancelService;
        this.matchReadyService = matchReadyService;
    }

    /**
     * 매칭 요청을 처리하는 엔드포인트.
     *
     * @param matchRequest 매칭 요청 DTO
     * @return 매칭 처리 결과 응답
     */
    @PostMapping("/matching")
    public ApiResponse<String> matching(@RequestBody MatchRequest matchRequest) {
        try {

            UserContext.setUserId(matchRequest.getId());
            // 매칭 요청 처리 서비스 호출
            matchRequestProcessingService.processMatchRequest(matchRequest);

            return ApiResponse.ok("매칭요청 성공");
        } catch (Exception e) {
            return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), "매칭요청실패");
        }
    }


/**
 * 유저의 매칭 취소 요청을 처리하는 엔드포인트
 *
 * @param matchCancelRequest 매칭 취소요청 DTO
 * @return 성공 메시지
 */
@PostMapping("/cancel")
public ApiResponse<MatchResponse> cancelMatch(@RequestBody MatchCancelRequest matchCancelRequest) {
    try {
        matchCancelService.cancelMatching(matchCancelRequest);
        return ApiResponse.ok(null);
    } catch (Exception e) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }
}

    /**
     * 준비완료
     * @param matchReady 준비완로 신호
     * @return
     */
    @PostMapping("/ready")
    public ApiResponse<MatchResponse> ReadyMatch(@RequestBody MatchReady matchReady){
        matchReadyService.UserReadyStatus(matchReady);
    return ApiResponse.ok(null);
}

    /**
     * 준비 취소
     * @param matchReady 준비취소 신호
     * @return
     */
    @PostMapping("/waiting")
    public ApiResponse<MatchResponse> WaitingMatch(@RequestBody MatchReady matchReady){
        matchReadyService.UserWitingStatus(matchReady);
        return ApiResponse.ok(null);
    }


    /**
     * 매치나가기 신호
     */
    @PostMapping("/matchleave")
    public ApiResponse<MatchResponse> MatchLeave(@RequestBody MatchReady matchReady){
        matchReadyService.UserMatchLeave(matchReady);
        return ApiResponse.ok(null);
    }


}
