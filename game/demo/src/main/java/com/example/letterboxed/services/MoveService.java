package com.example.letterboxed.services;


import java.io.File;
import java.io.FileWriter;

import javax.management.InvalidAttributeValueException;

import com.example.letterboxed.classes.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import org.springframework.stereotype.Service;


@Service
public class MoveService {

    public MoveService() {}

    public Move createMove(String gameId, String playerUsername, String word) throws InvalidAttributeValueException {
        if(GameLogic.valid_move(word, gameId))
        {
            Move move = new Move();
            move.setWord(word);
            move.setPlayerUsername(playerUsername);
            move.setGameId(gameId);

            try {
                File file = new File("game/demo/src/main/java/com/example/letterboxed/data/move.json");
                FileWriter fileWriter = new FileWriter(file, true);
                ObjectMapper mapper = new ObjectMapper();

                SequenceWriter seqWriter = mapper.writer().writeValuesAsArray(fileWriter);
                seqWriter.write(move);
                seqWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            
            return move;
        }
        else{
            throw new InvalidAttributeValueException("Invalid move");
        }
    }

    public boolean isPlayerTurn(Game game, Player firstPlayer, Player secondPlayer) {
        return GameLogic.playerTurn();
    }


}