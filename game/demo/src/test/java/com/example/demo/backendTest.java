package com.example.demo;
import java.lang.System;

import javax.management.InvalidAttributeValueException;

import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Move;
import com.example.letterboxed.classes.Player;
import com.example.letterboxed.services.GameService;
import com.example.letterboxed.services.MoveService;

public class backendTest {
    public static void main(String []args){
        System.out.print("Starting game\n");    
        Player myplayer = new Player("oot", "oot@gmail.com", "password");
        GameService myservice = new GameService();
        Game newGame = myservice.createNewGame(myplayer);
        System.out.println(newGame.getLetters());
        MoveService moveserve = new MoveService();
        try {
            Game newMove = moveserve.createMove(newGame.getId(), myplayer.getUserName(), "ace");
        } catch (InvalidAttributeValueException e) {
            System.out.println("worked");
        }
        try {
            Game newMove = moveserve.createMove(newGame.getId(), myplayer.getUserName(), "value");
        } catch (InvalidAttributeValueException e) {
            System.out.println("failed");
        }
        try {
            Game newMove = moveserve.createMove(newGame.getId(), myplayer.getUserName(), "value");
        } catch (InvalidAttributeValueException e) {
            System.out.println("worked 3");
        }
        
        
    }
    
}
