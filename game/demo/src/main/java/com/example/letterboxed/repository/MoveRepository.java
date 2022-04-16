package com.example.letterboxed.repository;

import com.example.letterboxed.classes.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MoveRepository extends CrudRepository<Move, String> {

    // List<Move> findByGame(Game game);
    // List<Move> findByGameAndPlayer(Game game, Player player);
    // int countByGameAndPlayer(Game game, Player player);
}