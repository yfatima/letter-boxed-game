package com.example.letterboxed.services;


import java.io.File;
import java.util.List;

import javax.management.InvalidAttributeValueException;

import com.example.letterboxed.classes.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

/**
 * This MoveService class methods are called by the MoveController to do backend data processing and logical work.
 */
@Service
public class MoveService {

    public MoveService() {}

    /**
     * This method validates the word and then adds it to the game and gives the player who made the move +1 score.
     * @param gameId
     * @param playerUsername
     * @param word
     * @return Game game (updated)
     * @throws InvalidAttributeValueException
     */
    public Game createMove(String gameId, String playerUsername, String word) throws InvalidAttributeValueException {
        if(GameLogic.valid_move(word, gameId))
        {
            Game game = null;
            try {
                File file = new File("game/demo/src/main/java/com/example/letterboxed/data/game.json");
                ObjectMapper objectMapper = new ObjectMapper();
                game = objectMapper.readValue(file, Game.class);
                //check if it is this users turn to make a move
                if (game.getGameStatus().equals(playerUsername)) {
                    //adds the word to words used list
                    game.getWordsUsed().add(word.toUpperCase());
                    //check if anyone won or assign next player turn 
                    if (GameLogic.winnerFound(playerUsername, game)) {
                        game.setGameStatus("finish");
                        game.setWinner(playerUsername);
                        updateGamesList(game);
                        String lostplayer;
                        if (game.getP1Id().equals(playerUsername) ) {
                            lostplayer = game.getP2Id();
                        } else {
                            lostplayer = game.getP1Id();
                        }
                        updatePlayersPoints(playerUsername, lostplayer);
                    } else if (game.getGameStatus().equals(game.getP1Id())) {
                        game.setGameStatus(game.getP2Id());
                    } else {
                        game.setGameStatus(game.getP1Id());
                    }
                    objectMapper.writeValue(file, game);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            return game;
        }
        else{
            System.out.println("exception raised\n");
            return new Game();
        }
    }

    /**
     * This method updates both player's overall count of games won and lost.
     * @param playerwonusername
     * @param playerlostusername
     * @return boolean
     */
    public boolean updatePlayersPoints(String playerwonusername, String playerlostusername) {
        List<Player> players = null;
        

        try {
            File file = new File("game/demo/src/main/java/com/example/letterboxed/data/players.json");
            ObjectMapper mapper = new ObjectMapper();
            
            players = mapper.readValue(file, new TypeReference<List<Player>>() {});
            
            for (Player player : players) {
                if (playerwonusername.equals(player.getUserName())) {
                    player.setGamesWon(player.getGamesWon() + 1);
                }
                if (playerlostusername.equals(player.getUserName())) {
                    player.setGamesLost(player.getGamesLost() + 1);
                }
            }
            //last step
            mapper.writeValue(file, players);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * This methods adds the game to overall games list after it is complete.
     * @param game
     * @return boolean
     */
    public boolean updateGamesList(Game game) {
        File file = new File("game/demo/src/main/java/com/example/letterboxed/data/games.json");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Game> games = null;
        try {
            games = objectMapper.readValue(file, new TypeReference<List<Game>>() {});
            games.add(game);
            objectMapper.writeValue(file, games);
            return true;

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }

    /**
     * This method skips the turn of the player with the given username in the game.
     * @param playerUsername
     * @return Game game (updated)
     */
    public Game skipMove(String playerUsername) {
        Game game = null;

            try {
                File file = new File("game/demo/src/main/java/com/example/letterboxed/data/game.json");
                ObjectMapper objectMapper = new ObjectMapper();
                game = objectMapper.readValue(file, Game.class);
                //check if it is this users turn to make a move
                if (game.getGameStatus().equals(playerUsername)) {
                    if (game.getGameStatus().equals(game.getP1Id())) {
                        game.setGameStatus(game.getP2Id());
                    } else {
                        game.setGameStatus(game.getP1Id());
                    }
                    objectMapper.writeValue(file, game);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        return game;
    }

}