package com.example.letterboxed.classes;

import java.util.List;

public class Game {
    private String id;
    private String p1Id;
    private String p2Id;
    private String gameStatus;
    private List<Character> letters;
    private List<String> wordsUsed;
    private Integer winScore;
    
    public Game(){}

    public Game(String id, String p1Id, String p2Id, String gameStatus, List<Character> letters, List<String> wordsUsed,
            Integer winScore) {
        this.id = id;
        this.p1Id = p1Id;
        this.p2Id = p2Id;
        this.gameStatus = gameStatus;
        this.letters = letters;
        this.wordsUsed = wordsUsed;
        this.winScore = winScore;
    }

    public List<String> getWordsUsed() {
        return wordsUsed;
    }

    public void setWordsUsed(List<String> wordsUsed) {
        this.wordsUsed = wordsUsed;
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
    
    public void setLetters(List<Character> letters) {
        this.letters = letters;
    }
    
    public List<Character> getLetters() {
        return letters;
    }

    public String getP1Id() {
        return this.p1Id;
    }

    public void setP1Id(String p1Id) {
        this.p1Id = p1Id;
    }

    public String getP2Id() {
        return this.p2Id;
    }

    public void setP2Id(String P2Id) {
        this.p2Id = P2Id;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String status) {
        this.gameStatus = status;
    }

    
}