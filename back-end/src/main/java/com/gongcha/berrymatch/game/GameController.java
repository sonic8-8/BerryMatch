package com.gongcha.berrymatch.game;

import com.gongcha.berrymatch.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    /**
     * 해당 유저가 진행한 모든 경기 반환
     * @param userId (리스트를 보고자 하는 유저의 id)
     * @return List<Game>
     */
    @GetMapping("/record/{userId}")
    public ApiResponse<List<Game>> loadAllGames(@PathVariable Long userId){
        return ApiResponse.ok(gameService.loadAllGames(userId));
    }

    /**
     * 특정 경기 점수 기록 전체 반환 (투표하게 하기 위해)
     * @param gameId (임시 등록된 경기 결과 중 가져오고 싶은 경기의 id)
     * @return List<GameResultTemp>
     */
    @GetMapping("/record/{gameId}")
    public ApiResponse<List<GameResultTemp>> loadAllInputRecord(@PathVariable Long gameId){
        return ApiResponse.ok(gameService.loadAllInputRecord(gameId));
    }

    /**
     * 경기 종료 후 경기 기록 작성 가능 상태 확인
     * @param game
     * @return GameStatus 경기 진행 상태 (DURING, END)
     */
    @GetMapping("/check-ready-input")
    public ApiResponse<GameStatus> checkReadyInput(@RequestParam GameDTO game) {
        return ApiResponse.ok(gameService.checkReadyInput(game));
    }

    /**
     * 경기 기록 제출 후 투표 가능 상태 확인
     * @param gameResultTemp (
     * @return GameRecordTempStatus (최종 결과를 업데이트)
     */
    @GetMapping("/check-ready-vote")
    public ApiResponse<GameRecordTempStatus> checkReadyVote(@RequestParam GameDTO gameResultTemp) {
        return ApiResponse.ok(gameService.checkReadyVote(gameResultTemp));
    }

    /**
     * 게시글 작성 가능 상태 확인
     * @param game (경기 결과 기록)
     * @return GameStatus (경기 기록 상태)
     */
    @GetMapping("/check-ready-post")
    public ApiResponse<GameStatus> checkReadyPost(@RequestParam GameDTO game) {
        return ApiResponse.ok(gameService.checkReadyPost(game));
    }

    /**
     * 경기 기록 제출
     * @param gameResultTemp (경기 결과 기록)
     */
    @PostMapping("/submit-record")
    public ApiResponse<String> submitRecord(@RequestParam GameDTO gameResultTemp) {
        gameService.submitRecord(gameResultTemp);
        return ApiResponse.ok("record submit success");
    }

    /**
     * 경기 결과 투표 제출
     * @param gameResultTemp (투표한 경기 기록)
     */
    @PostMapping("/submit-vote")
    public ApiResponse<String> submitVote(@RequestParam GameDTO gameResultTemp) {
        gameService.submitVote(gameResultTemp);
        return ApiResponse.ok("record votes submit success");
    }


}
