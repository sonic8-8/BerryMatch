package com.gongcha.berrymatch.game;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameResultTempRepository extends JpaRepository<GameResultTemp,Long> {

    List<GameResultTemp> findDistinctResultsByGameId(Long gameId);

    List<GameResultTemp> findAllByGame(Game game);
}
