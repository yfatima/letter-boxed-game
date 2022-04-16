package com.example.letterboxed.services;


import java.io.File;
import java.io.FileWriter;

import com.example.letterboxed.classes.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import org.springframework.stereotype.Service;

@Service
public class MoveService {

    public MoveService() {}

    public Move createMove(String gameId, String playerUsername, Move moveDTO) {
        Move move = new Move();
        move.setWord(moveDTO.getWord());
        move.setPlayerUsername(playerUsername);
        move.setGameId(gameId);

        try {
            File file = new File("../data/move.json");
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

    public boolean isPlayerTurn(Game game, Player firstPlayer, Player secondPlayer) {
        return GameLogic.playerTurn();
    }


}