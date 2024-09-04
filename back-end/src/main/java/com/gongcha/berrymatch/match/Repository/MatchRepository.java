// MatchRepository.java
package com.gongcha.berrymatch.match.Repository;

import com.gongcha.berrymatch.match.domain.Match;
import com.gongcha.berrymatch.match.domain.MatchFullStatus;
import com.gongcha.berrymatch.match.domain.MatchStatus;
import com.gongcha.berrymatch.match.domain.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {



    @Query("SELECT m FROM Match m WHERE m.id = :id")
    Optional<Match> findByIdForUpdate(@Param("id") Long id);

    Optional<Match> findFirstByFullStatusAndSportAndMatchStatusAndCurrentSizeLessThanOrderByMatchedAtAsc(
            MatchFullStatus fullStatus, Sport sport, MatchStatus matchStatus, int currentSize);

    /**
     * @param matchId must not be {@literal null}.
     * @return
     */
    Optional<Match> findById(Long matchId);


    /**
     * @param status 매치테이블 경기상태
     * @return 경기가끝난 매치테이블 항목
     */
    List<Match> findByMatchStatus(MatchStatus status);
}