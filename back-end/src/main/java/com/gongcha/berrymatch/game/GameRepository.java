package com.gongcha.berrymatch.game;

import com.gongcha.berrymatch.match.domain.Match;
import com.gongcha.berrymatch.match.domain.MatchUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game,Long> {

    String findGameStatusByMatch(Match match);

    GameDTO findAllByMatchId(MatchUser user);

    GameStatus findGameStatusBy(Game game);

    GameDTO findAllByGame(Game game);

    List<Game> findAllByMatchUser(Long userId);
}
