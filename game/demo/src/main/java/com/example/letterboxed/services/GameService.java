package com.example.letterboxed.services;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.logging.*;
import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Player;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This GameService class methods are called by the GameController to do backend data processing and logical work.
 */
@Service
public class GameService {
    private static Logger logger = Logger.getLogger("letterboxed");
    public GameService() {
    }

    /**
     * This method creates the new game with the given player assigned as player 1 in the game.
     * Each game has 4 or less vowels chosen randomly and has 12  or less consonants chosen randomly as well.
     * @param player
     * @return Game game (new)
     */
    public Game createNewGame(Player player) {
        try {
            Handler logfile = new FileHandler("%t/logs.log");
            Logger.getLogger("").addHandler(logfile);
            logger.setLevel(Level.ALL);
        } catch (SecurityException e1) {

            e1.printStackTrace();
        } catch (IOException e1) {

            e1.printStackTrace();
        }
        logger.log(Level.FINE, "player "+player+" creating new game");
        //create the letters available for the game
        Random random = new SecureRandom();
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
        game.setWinScore(2);
        game.setId(Integer.toString(Math.abs(random.nextInt())));
        game.setGameStatus("active");
        game.setLetters(letters);
        game.setWordsUsed(new ArrayList<String>());
        game.setWinner("not decided");

        // find the game in games json file and add player 1 
        // then create a new json file for the game
       
        File file2 = new File("game/demo/src/main/java/com/example/letterboxed/data/game.json");
        ObjectMapper objectMapper2 = new ObjectMapper();
        try {
            objectMapper2.writeValue(file2, game);
            logger.log(Level.FINE,"game created successfuly");
            return game;
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.WARNING,"Can't write game in game.json file");
            return game;
        }
    }

    /**
     * This method adds the given player as player 2 in the game and starts the game by changing gamestatus.
     * @param player
     * @param gameId
     * @return Game game (updated)
     */
    public Game joinGame(Player player, String gameId) {
        // find the game in json file and add player 2
        try {
            Handler logfile = new FileHandler("%t/logs.log");
            Logger.getLogger("").addHandler(logfile);
            logger.setLevel(Level.ALL);
        } catch (SecurityException e1) {

            e1.printStackTrace();
        } catch (IOException e1) {

            e1.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("game/demo/src/main/java/com/example/letterboxed/data/game.json");

        Game game = null;
        try {
            game = objectMapper.readValue(file, Game.class);
            //System.out.println(game.getP1Id().equals(player.getUserName()));
            if (game.getId().equals(gameId) && game.getP2Id().equals("none") && !(game.getP1Id().equals("none") || game.getP1Id().equals(player.getUserName()))) {
                game.setP2Id(player.getUserName());
                game.setGameStatus(game.getP1Id());
                objectMapper.writeValue(file, game);
            }
            logger.log(Level.FINE,"player "+player.getUserName()+" joined game "+gameId.toString()+" successfully");
            return game;
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.FINE,"Can't write game in game.json file");
            return game;
        }
        
    }

    /**
     * This method search the game json data file to find the game with the given game id
     * @param id
     * @return Game game
     */
    public Game getGame(String id) {
        try {
            Handler logfile = new FileHandler("%t/logs.log");
            Logger.getLogger("").addHandler(logfile);
            logger.setLevel(Level.ALL);
        } catch (SecurityException e1) {

            e1.printStackTrace();
        } catch (IOException e1) {

            e1.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("game/demo/src/main/java/com/example/letterboxed/data/game.json");

        Game game = null;
        try {
            game = objectMapper.readValue(file, Game.class);
            //System.out.println(game);
            if (game.getId().equals(id)) {
                return game;
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.WARNING,"Error in reading the game from game.json file");
            return game;
        }
        
        return game;
    }

    /**
     * This methods returns the list of all past games.
     * @return List<Game> games
     */
    public List<Game> getGames() {
        try {
            Handler logfile = new FileHandler("%t/logs.log");
            Logger.getLogger("").addHandler(logfile);
            logger.setLevel(Level.ALL);
        } catch (SecurityException e1) {

            e1.printStackTrace();
        } catch (IOException e1) {

            e1.printStackTrace();
        }
        File file = new File("game/demo/src/main/java/com/example/letterboxed/data/games.json");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Game> games = null;
        try {
            games = objectMapper.readValue(file, new TypeReference<List<Game>>() {});
            return games;

        } catch (IOException e1) {
            e1.printStackTrace();
            logger.log(Level.SEVERE, "Error in reading games from games.json file");
            System.out.println("Error in reading the games from games.json file");
            return games;
        }
        
    }

    /**
     * This methods changes the gamestatus by setting it to the username of the player whose turn is next.
     * @param nextPlayerUsername
     * @param id
     * @return boolean
     */
    public boolean updateGameStatus(String nextPlayerUsername, String id) {
        try {
            Handler logfile = new FileHandler("%t/logs.log");
            Logger.getLogger("").addHandler(logfile);
            logger.setLevel(Level.ALL);
        } catch (SecurityException e1) {

            e1.printStackTrace();
        } catch (IOException e1) {

            e1.printStackTrace();
        }
        
        Game game = getGame(id);
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("game/demo/src/main/java/com/example/letterboxed/data/game.json");
        try {
            game.setGameStatus(nextPlayerUsername);
            objectMapper.writeValue(file, game);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.WARNING,"Error in writing the updated gane in game.json file");
            return false;
        }
    }


}
