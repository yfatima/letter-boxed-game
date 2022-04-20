package com.example.letterboxed.services;

import java.io.File;

import java.util.List;

import com.example.letterboxed.classes.Game;
import com.fasterxml.jackson.databind.ObjectMapper;



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


        
        return true;
    }

}
