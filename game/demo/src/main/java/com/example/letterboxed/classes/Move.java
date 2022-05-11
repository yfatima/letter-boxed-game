package com.example.letterboxed.classes;

/**
 * This move class is used to create an instance move object that can contain information about a move made in the game by a player
 * this class also holds setter and getter for all the attributes of move object
 */
public class Move {

    private String gameId;
    private String playerUsername;
    private String word;
    
    public Move() {}

    public Move (String gameId, String playerUsername, String word) {
        this.gameId = gameId;
        this.playerUsername = playerUsername;
        this.word = word;
    }

    public String getGameId() {
        return this.gameId;
    }

    public void setGameId(String id) {
        this.gameId = id;
    }

    public String getPlayer() {
        return this.playerUsername;
    }

    public void setPlayerUsername(String username) {
        this.playerUsername = username;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String string) {
        this.word = string;
    }

    

}
