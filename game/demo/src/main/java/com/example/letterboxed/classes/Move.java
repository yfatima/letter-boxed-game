package com.example.letterboxed.classes;

public class Move {

    private Game game;
    private Player player;
    private String word;
    
    public Move() {}

    public Move (Game game, Player player, String word) {
        this.game = game;
        this.player = player;
        this.word = word;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    

}
