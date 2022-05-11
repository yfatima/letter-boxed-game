package com.example.letterboxed.classes;

import java.util.List;

/**
 * This game class is used to create an instance every game with an unique game id and attributes 
 * depending on the players, letters, and etc.
 * this class also holds setter and getter for all the attributes of game object
 */
public class Game {
    private String id;
    private String p1Id;
    private String p2Id;
    private String gameStatus;
    private List<Character> letters;
    private List<String> wordsUsed;
    private Integer winScore;
    private String winner;
    
    public Game(){}

    public Game(String id, String p1Id, String p2Id, String gameStatus, List<Character> letters, List<String> wordsUsed,
            Integer winScore, String winner) {
        this.id = id;
        this.p1Id = p1Id;
        this.p2Id = p2Id;
        this.gameStatus = gameStatus;
        this.letters = letters;
        this.wordsUsed = wordsUsed;
        this.winScore = winScore;
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public List<String> getWordsUsed() {
        return wordsUsed;
    }

    public void setWordsUsed(List<String> wordsUsed) {
        this.wordsUsed = wordsUsed;
    }

    public Integer getWinScore() {
        return this.winScore;
    }


    public void setWinScore(Integer winScore) {
        this.winScore = winScore;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id; 
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
        return this.gameStatus;
    }

    public void setGameStatus(String status) {
        this.gameStatus = status;
    }

    
}