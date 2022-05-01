package com.example.letterboxed.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Player;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;

import net.minidev.json.JSONObject;



public class GameLogic {

    public GameLogic() {}

    public static boolean playerTurn() {
        return false;
    }

    //TODO: NEED TO CHECK WITH DICTIONARY FOR REAL WORDS
    public static boolean valid_move(String word, String gameId){
        //check if all letters are in the game
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("game/demo/src/main/java/com/example/letterboxed/data/game.json");
        List <Character> letters=null;
        Game game = null;
        try {
            game = objectMapper.readValue(file, Game.class);
            if (game.getId().equals(gameId)) {
                letters = game.getLetters();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        char[] array = word.toUpperCase().toCharArray();
        for(char i:array)
        {
            if(!letters.contains(i))
            {
                System.out.println("Word " + word +" is invalid. ");
                return false;
            }
        }

        //check if word is already used
        List<String> previousWords = game.getWordsUsed();
        if (previousWords.contains(word.toUpperCase()))
        {
            System.out.println("Word " + word+ " is already used");
            return false;
        }
        
        previousWords.add(word.toUpperCase());
        game.setWordsUsed(previousWords);
        try {
            JSONObject root = objectMapper.readValue(file, JSONObject.class);
            root.put(game.getId(),game);
            FileWriter fileWriter = new FileWriter(file, true);
            SequenceWriter seqWriter = objectMapper.writer().writeValuesAsArray(fileWriter);
            seqWriter.write(game);
            seqWriter.close();
        } catch (Exception e) {
            
        }
        
        return true;
    }

    public static boolean winnerFound(String username, Game game) {
        System.out.println("in winnerfound\n");
        int playerScore = 0;

        List<Player> players = null;

        try {
            File file = new File("game/demo/src/main/java/com/example/letterboxed/data/players.json");
            ObjectMapper objectMapper = new ObjectMapper();
            players = objectMapper.readValue(file, new TypeReference<List<Player>>() {});
        

            for (Player player2 : players) {
                if (username.equals(player2.getUserName())) {
                    playerScore = player2.getScore();
                }
            }

            
        } catch (Exception e1) {
            e1.printStackTrace();
        }


        if (playerScore+1 == game.getWinScore()) {
            return true;
        }
        return false;
    }

}
