package com.example.letterboxed.classes;

import java.util.List;

public class Game {
    private String id;
    private Player P1;
    private Player P2;
    private String gameStatus;
    private Integer boxHeight;
    private Integer boxWidth;
    private List<String> letters;
    private List<String> wordUsed;

    public Game(){

    }

    public Game(String id, Player P1, Player P2,  String gameStatus, Integer boxWidth, Integer boxHeight, List<String> letters) {
        this.id = id;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.letters = letters;
        this.gameStatus = gameStatus;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id; 
    }

    public void setBoxWidth(Integer width) {
        this.boxWidth = width;
    }

    public Integer getBoxWidth() {
        return boxWidth;
    }

    public void setBoxHeight(Integer height) {
        this.boxHeight = height;
    }

    public Integer getBoxHeight() {
        return boxHeight;
    }
    
    public void setLetters(List<String> letters) {
        this.letters = letters;
    }
    
    public List<String> getLetters() {
        return letters;
    }

    public List<String> getWordUsed() {
        return wordUsed;
    }

    public void setWordUsed(List<String> wordUsed) {
        this.wordUsed = wordUsed;
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

    
}