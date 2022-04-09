package com.example.letterboxed.repository;

import java.util.List;
import java.util.Optional;

import com.example.letterboxed.classes.Game;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, String>{
    Game findById(String id);
    List<Game> getGames();
}