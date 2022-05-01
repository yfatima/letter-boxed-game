package com.example.letterboxed.services;


import java.io.File;

import javax.management.InvalidAttributeValueException;

import com.example.letterboxed.classes.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;


@Service
public class MoveService {

    public MoveService() {}

    public Game createMove(String gameId, String playerUsername, String word) throws InvalidAttributeValueException {
        if(GameLogic.valid_move(word, gameId))
        {
            // Move move = new Move();
            // move.setWord(word);
            // move.setPlayerUsername(playerUsername);
            // move.setGameId(gameId);


            Game game = null;

            try {
                File file = new File("game/demo/src/main/java/com/example/letterboxed/data/game.json");
                ObjectMapper objectMapper = new ObjectMapper();
                game = objectMapper.readValue(file, Game.class);
                //check if it is this users turn to make a move
                if (game.getGameStatus().equals(playerUsername)) {
                    if (GameLogic.winnerFound(playerUsername, game)) {
                        game.setGameStatus("finish");
                    } else if (game.getGameStatus().equals(game.getP1Id())) {
                        game.setGameStatus(game.getP2Id());
                    } else {
                        game.setGameStatus(game.getP1Id());
                    }
                    game.getWordsUsed().add(word.toUpperCase());
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
            // throw new InvalidAttributeValueException("Invalid move");
        }
    }

    public boolean isPlayerTurn(Game game, Player firstPlayer, Player secondPlayer) {
        return GameLogic.playerTurn();
    }

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