package com.gongcha.berrymatch.match.Controller;

import com.gongcha.berrymatch.ApiResponse;
import com.gongcha.berrymatch.match.DTO.*;
import com.gongcha.berrymatch.match.Service.GetMatchUserService;
import com.gongcha.berrymatch.match.Service.MatchCancelService;
import com.gongcha.berrymatch.match.Service.MatchReadyService;
import com.gongcha.berrymatch.match.Service.MatchRequestProcessingService;
import com.gongcha.berrymatch.match.ThreadLocal.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class MatchController {
    private static final Logger logger = LoggerFactory.getLogger(MatchController.class);
    private final MatchRequestProcessingService matchRequestProcessingService;
    private final MatchCancelService matchCancelService;
    private final MatchReadyService matchReadyService;
    private final GetMatchUserService getMatchUserService;


    @Autowired
    public MatchController(MatchRequestProcessingService matchRequestProcessingService, MatchCancelService cancelMatching, MatchCancelService matchCancelService, MatchReadyService matchReadyService, GetMatchUserService getMatchUserService
    ) {
        this.matchRequestProcessingService = matchRequestProcessingService;
        this.matchCancelService = matchCancelService;
        this.matchReadyService = matchReadyService;
        this.getMatchUserService = getMatchUserService;
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
            System.out.println("컨트롤러"+UserContext.getUserId());

            // 매칭 요청 처리 서비스 호출
            matchRequestProcessingService.processMatchRequest(matchRequest);


            return ApiResponse.ok("대기열 입장중");

        } catch (Exception e) {
            // 이미 매칭 중이거나 대기 중인 유저에 대한 예외 처리
            return ApiResponse.of(HttpStatus.CONFLICT, "이미 매칭 중입니다.", "매칭요청실패");
        }
    }


    @GetMapping("/matchusers")
    public ApiResponse<List<MatchUserResponse>> getMatchUser(@ModelAttribute MatchUserRequest matchUserRequest){
        System.out.println("매칭페이지 진입"+matchUserRequest.getId());
        List<MatchUserResponse> matchUserResponses = getMatchUserService.getMatchUser(matchUserRequest);

        return ApiResponse.ok(matchUserResponses);
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
    public ApiResponse<MatchReady> ReadyMatch(@RequestBody MatchReady matchReady){

        System.out.println("신호들어옴"+matchReady.getId());
        MatchReady response= matchReadyService.userReadyStatus(matchReady);
        if (response.isAllUsersReady()) {
            return ApiResponse.ok(response);
        } else {
            return ApiResponse.ok(response);
        }
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
