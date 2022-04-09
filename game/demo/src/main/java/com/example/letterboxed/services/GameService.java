package com.example.letterboxed.services;

import java.util.List;

import com.example.letterboxed.DTO.GameDTO;
import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Player;
import com.example.letterboxed.repository.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Game g1 = new Game();

    private final GameRepository gameRepository;


    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createNewGame(Player player, GameDTO gameDTO) {
        Game game = new Game();
        game.setP1(player);

        gameRepository.save(game);

        return game;
    }
    public Game getG1() {
        return g1;
    }

    public Game joinGame(Player player, GameDTO gameDTO) {
        Game game =  getGame(gameDTO.getId());
        game.setP2(player);
        gameRepository.save(game);
        return game;

    }

    public Game getGame(String id) {
        return gameRepository.findById(id);
    }

    public List<Game> getGames() {
        return gameRepository.getGames();
    }
}

