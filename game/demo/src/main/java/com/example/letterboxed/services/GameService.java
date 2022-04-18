package com.example.letterboxed.services;

import java.io.File;
import java.util.List;

import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Player;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Game g1 = new Game();

    public GameService() {
    }

    public Game createNewGame(Player player, int gameNumber) {
        Game game = new Game();
        game.setP1(player);
        game.setId("game" + gameNumber);
        game.setGameStatus("inactive");

        // find the game in games json file and add player 1 
        // then create a new json file for the game
        File file = new File("../data/games.json");
        File file2 = new File("../data/game2.json");

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Game> games = objectMapper.readValue(file, new TypeReference<List<Game>>() {});
            for (Game game2 : games) {
                if (game.getId().equals(game2.getId())) {
                    game.setLetters(game2.getLetters());
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        ObjectMapper objectMapper2 = new ObjectMapper();
        try {
            objectMapper2.writeValue(file2, game);
            return game;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return game;
    }

    public Game getG1() {
        return g1;
    }

    public Game joinGame(Player player, int gameNumber) {
        // find the game in json file and add player 2

        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("game/demo/src/main/java/com/example/letterboxed/data/game.json");

        Game game = null;
        try {
            game = objectMapper.readValue(file, Game.class);
            String gameid = "game" + gameNumber;
            if (game.getId().equals(gameid) && game.getP1() != player) {
                game.setP2(player);
                game.setGameStatus("active");
                objectMapper.writeValue(file, game);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return game;

    }

    public Game getGame(String id) {

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("game/demo/src/main/java/com/example/letterboxed/data/game.json");

        Game game = null;
        try {
            game = objectMapper.readValue(file, Game.class);
            if (game.getId().equals(id)) {
                return game;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return game;

    }

    public List<Game> getGames() {
        File file = new File("game/demo/src/main/java/com/example/letterboxed/data/games.json");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Game> games = null;
        try {
            games = objectMapper.readValue(file, new TypeReference<List<Game>>() {});
            return games;

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return games;
    }

    public boolean updateGameStatus(String nextPlayerUsername, String id) {
        Game game = getGame(id);
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File(".game/demo/src/main/java/com/example/letterboxed/data/game.json");
        try {
            game = objectMapper.readValue(file, Game.class);
            if (game.getId().equals(id)) {
                game.setGameStatus(nextPlayerUsername);
                objectMapper.writeValue(file, game);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
