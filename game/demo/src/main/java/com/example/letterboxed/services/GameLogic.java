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
    public boolean valid_move(String word, String gameId){
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
        char[] array = word.toCharArray();
        for(char i:array)
        {
            if(!letters.contains(i))
            {
                return false;
            }
        }
       
        return false;
    }

}
