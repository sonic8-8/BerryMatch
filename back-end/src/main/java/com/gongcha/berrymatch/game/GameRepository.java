package com.gongcha.berrymatch.game;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game,Long> {

    GameStatus findGameStatusByGame(Game game);

    Game findAllByGame(Game game);

    List<Game> findAllByUser(Long userId);
}
