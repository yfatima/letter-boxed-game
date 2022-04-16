package com.example.letterboxed.repository;

import com.example.letterboxed.classes.Game;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, String>{
    // Game findOneById(String id);
    // List<Game> getGames();
}