package com.example.letterboxed.Controller;

import java.util.List;
import com.example.letterboxed.classes.Player;
import com.example.letterboxed.services.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @RequestMapping("")
    public String defaultPlayer() {
        return "player default path";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Boolean createAccount(@RequestBody Player newPlayer) {
        System.out.println(newPlayer);
        return playerService.createNewPlayer(newPlayer);
    }

    @RequestMapping(value = "/logged", method = RequestMethod.GET, produces =MediaType.APPLICATION_JSON_VALUE)
    public Player getLoggedPlayer() {
        return playerService.getLoggedUser();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Boolean authPlayer(@RequestBody Player newPlayer) {
        return playerService.authPlayer(newPlayer);
    }

    @RequestMapping(value = "/getplayer/{username}", method = RequestMethod.GET)
    public Player getPlayerByUsername(@PathVariable String username) {
        return playerService.getPlayerByUsername(username);
    }

    @RequestMapping(value = "/getplayerscore/{username}", method = RequestMethod.GET)
    public Integer getPlayerScore(@PathVariable String username) {
        return playerService.getPlayerScore(username);
    }

    @RequestMapping(value = "/clearplayerscore", method = RequestMethod.POST)
    public Integer clearPlayerScore(@PathVariable String username) {
        return playerService.clearPlayerScore(username);
    }

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public List<Player> getPlayers() {
        return playerService.listPlayers();
    }


}