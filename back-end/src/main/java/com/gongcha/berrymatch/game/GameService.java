package com.gongcha.berrymatch.game;

import com.gongcha.berrymatch.match.domain.Match;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameResultTempRepository gameResultTempRepository;

    // 해당 경기에 대한 경기 진행 여부 반환
    public String loadGameSatatus(Match match){
        return gameRepository.findGameStatusByMatch(match);
    }

    // 경기 기록이 들어오면 임시 결과 DB에 저장
    @Transactional
    public void saveInputRecord(GameDTO gameResultTemp){
        GameResultTemp gameRecordInput = GameDTO.builder()
                .game(gameResultTemp.getGame())
                .user(gameResultTemp.getUser())
                .resultTeamA(gameResultTemp.getResultTeamA())
                .resultTeamB(gameResultTemp.getResultTeamB())
                .build();
        gameResultTempRepository.save(gameRecordInput);
    }

    // 유저의 모든 경기 리턴
    public List<Game> loadAllGames(Long userId){
        return gameRepository.findAllByMatchUser(userId);
    }

    // 해당 경기에 등록된 모든 점수 기록 리턴
    public List<GameResultTemp> loadAllInputRecord(Long gameId){
        return gameResultTempRepository.findDistinctResultsByGameId(gameId);
    }

    // 경기 기록 가능 상태 확인
    public GameStatus checkReadyInput(GameDTO game) {
        return gameRepository.findGameStatusBy(game.getGame());
    }

    // 경기 투표 가능 상태 확인: 모든 유저의 경기기록 완료 or 경기 시간으로부터 24시간 넘었을 경우
    public GameRecordTempStatus checkReadyVote(GameDTO gameResultTemp) {
        GameDTO gameInfo = gameRepository.findAllByGame(gameResultTemp.getGame());
        List<GameResultTemp> recordTempList = gameResultTempRepository.findAllByGame(gameResultTemp.getGame());
        int recordCnt = 0;
        for(GameResultTemp r : recordTempList){
            recordCnt++;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime gameTime = gameInfo.getMatch().getMatchedAt();
        long duration = Duration.between(gameTime,now).toHours() % 24;
        if(recordCnt == 12 || duration > 24){
            return GameRecordTempStatus.FINALLY_UPDATE;
        }else{
            return GameRecordTempStatus.PERSONNAL_UPDATE;
        }
    }

    // 제출된 경기 기록 투표 Data 정리 후 최종 결과 등록
    @Transactional
    public void finalizeGameRecords(GameDTO game){
        // 해당하는 경기에 대해 유저들이 제출한 모든 기록을 가져옴
        List<GameResultTemp> tempResultList = gameResultTempRepository.findAllByGame(game.getGame());
        // 기록들 중 가장 투표수가 많은 기록을 찾음
        int maxVotes = 0;
        int maxVotesResultTeamA = 0;
        int maxVotesResultTeamB = 0;
        for(GameResultTemp r : tempResultList){
            if(r.getVotes() > maxVotes){
                maxVotes = r.getVotes();
                maxVotesResultTeamA = r.getResultTeamA();
                maxVotesResultTeamB = r.getResultTeamB();
            }
        }
        // 투표수가 가장 많은 기록을 해당 경기의 경기 기록으로 저장
        GameDTO gameRecord = gameRepository.findAllByGame(game.getGame());
        gameRecord.setResultTeamA(maxVotesResultTeamA);
        gameRecord.setResultTeamB(maxVotesResultTeamB);
        gameRepository.save(gameRecord);
    }


    // 게시물 작성 가능 상태 확인(경기 기록 투표 제출 Data 정리 후, 최종 경기 결과 등록 되어 있으면)
    public GameStatus checkReadyPost(GameDTO game) {
        // 로직: 게시물 작성 가능 상태인지 확인
        GameDTO gameRecord = gameRepository.findAllByGame(game.getGame());
        if(gameRecord.getResultTeamA() == 0 && gameRecord.getResultTeamB() == 0){
            return GameStatus.END;
        }else{
            return GameStatus.OVER;
        }
    }

    // 경기 기록 DB 등록
    @Transactional
    public void submitRecord(GameDTO gameResultTemp) {
        GameResultTemp gameRecordInput = GameDTO.builder()
                        .game(gameResultTemp.getGame())
                        .user(gameResultTemp.getUser())
                        .resultTeamA(gameResultTemp.getResultTeamA())
                        .resultTeamB(gameResultTemp.getResultTeamB())
                        .getClass()
                        .build();
        gameResultTempRepository.save(gameRecordInput);
    }

    // 투표 기록 DB 등록
    @Transactional
    public void submitVote(GameDTO gameResultTemp) {
        List<GameResultTemp> submitRecordList = gameResultTempRepository.findAllByGame(gameResultTemp.getGame());
        // gameDTO로 받은 기록 데이터와 일치하는 것에 votes++
        for(GameResultTemp r:submitRecordList){
            if (r.getResultTeamA() == gameResultTemp.getResultTeamA() && r.getResultTeamB() == gameResultTemp.getResultTeamB()) {
                r.setVotes(r.getVotes()+1);
            } else {
                throw new IllegalArgumentException("Invalid Record ID");
            }
        }
    }

}
