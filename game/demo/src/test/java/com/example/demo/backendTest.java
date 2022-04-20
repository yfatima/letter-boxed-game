package com.example.demo;
import java.lang.System;
import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Player;
import com.example.letterboxed.services.GameService;

public class backendTest {
    public static void main(String []args){
        System.out.print("Starting game");    
        Player myplayer = new Player("oot", "oot@gmail.com", "password");
        GameService myservice = new GameService();
        Game newGame = myservice.createNewGame(myplayer);
        System.out.println(newGame.getLetters());
    }
    
}
