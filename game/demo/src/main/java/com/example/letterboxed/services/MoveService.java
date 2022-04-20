package com.example.letterboxed.services;


import java.io.File;
import java.io.FileWriter;

import com.example.letterboxed.classes.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Service
public class MoveService {

    public MoveService() {}

    public Move createMove(String gameId, String playerUsername, String word) {
        Move move = new Move();
        move.setWord(word);
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