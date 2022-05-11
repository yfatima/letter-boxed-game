package com.example.letterboxed.classes;

/**
 * This player class is used to create an instance of player/user object when a person registers to our application.
 * this class also holds setter and getter for all the attributes of player object
 */
public class Player {

    private String userName;
    private String email;
    private String password;
    private Integer score;
    private Integer gamesLost;
    private Integer gamesWon;

    public Player() {}

    public Player(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.gamesLost = 0;
        this.gamesWon = 0;
        this.score = 0;
    }


    public Integer getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(Integer gamesLost) {
        this.gamesLost = gamesLost;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
