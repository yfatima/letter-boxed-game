package com.example.letterboxed.DTO;

import com.example.letterboxed.classes.Player;

/**
 * This MoveDTO class is used to extract word and player from request body of post request.
 */
public class MoveDTO {
    public String word;
    public Player player;
    public String gameid;

}
