package com.example.letterboxed.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.*;
import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Player;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;

import net.minidev.json.JSONObject;

/**
 * This GameLogic class performs input validation for the word and check for a
 * winner.
 */
public class GameLogic {
    private static Logger logger = Logger.getLogger("letterboxed");

    public GameLogic() {
    }

    /**
     * This method validates the word if it contains letters of the specfic game and
     * is a real world word.
     * 
     * @param word
     * @param gameId
     * @return boolean
     */
    public static boolean valid_move(String word, String gameId) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            FileHandler logfile = new FileHandler(
                    "game/demo/src/main/java/com/example/letterboxed/data/validatemovelogs.log", 2000, 1, true);
            Logger.getLogger("").addHandler(logfile);
            logger.setLevel(Level.ALL);

            // check if all letters are in the game
            File file = new File("game/demo/src/main/java/com/example/letterboxed/data/game" + gameId + ".json");

            List<Character> letters = null;
            Game game = null;
            // if (word.length() <3)
            // {
            // return false;
            // }
            try {
                game = objectMapper.readValue(file, Game.class);
                if (game.getId().equals(gameId)) {
                    letters = game.getLetters();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error in reading the game from game.json file");
                logger.log(Level.WARNING, "game not found", gameId);
                return false;
            }
            char[] array = word.toUpperCase().toCharArray();
            for (char i : array) {
                if (!letters.contains(i)) {
                    logger.log(Level.WARNING, "invalid word used by user", game.getGameStatus());
                    return false;
                }
            }

            // check if word is already used
            List<String> previousWords = game.getWordsUsed();
            if (previousWords.contains(word.toUpperCase())) {
                logger.log(Level.WARNING, "invalid word used by user", game.getGameStatus());
                return false;
            }

            // check if word exists
            String query = "https://od-api.oxforddictionaries.com:443/api/v2/entries/en-gb/" + word.toLowerCase()
                    + "?fields=pronunciations&strictMatch=true";
            System.out.println(query);
            previousWords.add(word.toUpperCase());
            game.setWordsUsed(previousWords);
            try {
                JSONObject root = objectMapper.readValue(file, JSONObject.class);
                root.put(game.getId(), game);
                FileWriter fileWriter = new FileWriter(file, true);
                SequenceWriter seqWriter = objectMapper.writer().writeValuesAsArray(fileWriter);
                seqWriter.write(game);
                seqWriter.close();
            } catch (IOException e) {
                logger.log(Level.WARNING, "invalid word used by user", game.getGameStatus());
                System.out.println("Error in reading/writing the game from/in game.json file");
                return false;
            }
            logger.log(Level.FINE, "word is valid");
            logfile.close();
            return true;
        } catch (SecurityException e1) {

            e1.printStackTrace();
            return false;
        } catch (IOException e1) {

            e1.printStackTrace();
            return false;
        }

    }

    /**
     * This method check if the player with the given username won the game or not.
     * 
     * @param username
     * @param game
     * @return boolean
     */
    public static boolean winnerFound(String username, Game game) {

        int playerScore = 0;
        // list of all players
        List<Player> players = null;

        try {
            File file = new File("game/demo/src/main/java/com/example/letterboxed/data/players.json");
            ObjectMapper objectMapper = new ObjectMapper();
            players = objectMapper.readValue(file, new TypeReference<List<Player>>() {
            });

            // find the player with the given username and get their score
            for (Player player2 : players) {
                if (username.equals(player2.getUserName())) {
                    playerScore = player2.getScore();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
        // check if they have won
        if (playerScore + 1 == game.getWinScore()) {
            try {
                FileHandler logfile = new FileHandler(
                        "game/demo/src/main/java/com/example/letterboxed/data/winnerfoundlog.log", 2000, 1, true);
                Logger.getLogger("").addHandler(logfile);
                logger.setLevel(Level.ALL);
                logger.log(Level.FINE, "player " + game.getGameStatus() + " won");
                logfile.close();
                return true;
            } catch (SecurityException e1) {
                e1.printStackTrace();
                return false;
            } catch (IOException e1) {
                e1.printStackTrace();
                return false;
            }

        }
        return false;

    }

}
