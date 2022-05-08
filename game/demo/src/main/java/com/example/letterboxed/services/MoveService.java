package com.example.letterboxed.services;


import java.io.File;
import java.util.List;

import javax.management.InvalidAttributeValueException;

import com.example.letterboxed.classes.*;
import com.fasterxml.jackson.core.type.TypeReference;
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
                    game.getWordsUsed().add(word.toUpperCase());
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
            // throw new InvalidAttributeValueException("Invalid move");
        }
    }

    public boolean isPlayerTurn(Game game, Player firstPlayer, Player secondPlayer) {
        return GameLogic.playerTurn();
    }

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