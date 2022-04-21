package com.example.letterboxed.services;

import java.io.File;
//import java.security.SecureRandom;
import java.util.List;

import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Player;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class GameService {
    private Game g1 = new Game();

    public GameService() {
    }

    public Game createNewGame(Player player) {
        System.out.println("Creating new game");
        //create the letters available for the game
        Random random = new Random();
        random.setSeed(12345);
        Character vowels []= {'A', 'E', 'I', 'O', 'U', 'Y'};
        Character consonants [] = {'B', 'C', 'D', 'F', 'G', 'H','J','K','L','M','N','P','Q','R','S','T','V','W','X','Y','Z'};
        ArrayList <Character> letters = new ArrayList<Character>();
        while (letters.size()<4)
        {
            
            int number = random.nextInt(vowels.length);
            if (letters.contains(vowels[number]))
            {
                continue;
            }
            else
            {
                letters.add(vowels[number]);
            }   
        }
        while (letters.size()<12)
        {
            int number = random.nextInt(consonants.length);
            if (letters.contains(consonants[number]))
            {
                continue;
            }
            else
            {
                letters.add(consonants[number]);
            }   
        }
        Collections.shuffle(letters);

        Game game = new Game();
        game.setP1Id(player.getUserName());
        game.setP2Id("none");
        game.setWinScore(3);
        game.setId(Integer.toString(random.nextInt()));
        game.setGameStatus("inactive");
        game.setLetters(letters);
        game.setWordsUsed(new ArrayList<String>());

        // find the game in games json file and add player 1 
        // then create a new json file for the game
       
        File file2 = new File("game/demo/src/main/java/com/example/letterboxed/data/game.json");
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

    public Game joinGame(Player player, String gameId) {
        // find the game in json file and add player 2

        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("game/demo/src/main/java/com/example/letterboxed/data/game.json");

        Game game = null;
        try {
            game = objectMapper.readValue(file, Game.class);
            System.out.println(game.getP1Id().equals(player.getUserName()));
            if (game.getId().equals(gameId) && game.getP2Id().equals("none") && !(game.getP1Id().equals("none") || game.getP1Id().equals(player.getUserName()))) {
                game.setP2Id(player.getUserName());
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
