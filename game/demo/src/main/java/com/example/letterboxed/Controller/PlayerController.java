package com.example.letterboxed.Controller;

import com.example.letterboxed.DTO.PlayerDTO;
import com.example.letterboxed.classes.Player;
import com.example.letterboxed.services.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Player createAccount(@RequestBody PlayerDTO newPlayerDTO) {
        Player newPlayer = playerService.createNewPlayer(newPlayerDTO);
        return newPlayer;
    }

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public void getPlayers() {
        playerService.listPlayers();}

    @RequestMapping(value = "/logged", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Player getLoggedPlayer() {
        return new Player();
    }


}