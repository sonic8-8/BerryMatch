package com.gongcha.berrymatch.match.Repository;

import com.gongcha.berrymatch.match.domain.Match;
import com.gongcha.berrymatch.match.domain.MatchUser;
import com.gongcha.berrymatch.match.domain.MatchUserReady;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchUserRepository extends JpaRepository<MatchUser, Long> {


    // 모든 매칭된 유저들 가져오기
    List<MatchUser> findAll();


    Optional<MatchUser> findByUserId(Long userId);

    boolean existsByMatch(Match match);


    /**
     * @param matchId 메치테이블 식별자
     * @param status 매치유저테이블 준비 상태
     * @return 매치유저테이블 준비완료/ 대기중 상태 항목
     */
    List<MatchUser> findByMatchIdAndStatus(Long matchId, MatchUserReady status);
}
