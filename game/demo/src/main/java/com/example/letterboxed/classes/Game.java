package com.example.letterboxed.classes;

import java.util.List;

public class Game {
    private String id;
    private Player P1;
    private Player P2;
    private String gameStatus;
    private List<String> letters;
    private Integer winScore;
    
    public Game(){

    }

    public Integer getWinScore() {
        return winScore;
    }


    public void setWinScore(Integer winScore) {
        this.winScore = winScore;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id; 
    }
    
    public void setLetters(List<String> letters) {
        this.letters = letters;
    }
    
    public List<String> getLetters() {
        return letters;
    }

    public Player getP1() {
        return P1;
    }

    public void setP1(Player p1) {
        P1 = p1;
    }

    public Player getP2() {
        return P2;
    }

    public void setP2(Player p2) {
        P2 = p2;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String status) {
        this.gameStatus = status;
    }

    
}