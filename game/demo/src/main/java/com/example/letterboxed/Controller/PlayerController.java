package com.example.letterboxed.Controller;

import java.util.List;

import com.example.letterboxed.DTO.PlayerDTO;
import com.example.letterboxed.classes.Player;
import com.example.letterboxed.services.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    private AuthenticationManager authManager;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Boolean createAccount(@RequestBody Player newPlayer) {
        System.out.println(newPlayer);
        return playerService.createNewPlayer(newPlayer);
    }

    // @RequestMapping(value = "/players", method = RequestMethod.GET)
    // public void getPlayers() {
    //     playerService.listPlayers();
    // }

    @RequestMapping(value = "/logged", method = RequestMethod.GET, produces =MediaType.APPLICATION_JSON_VALUE)
    public Player getLoggedPlayer() {
        return playerService.getLoggedUser();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Boolean authPlayer(@RequestBody Player newPlayer) {
        // newPlayer.setPassword(passwordEncoder.encode(newPlayer.getPassword()+newPlayer.getUserName()));
        // UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(newPlayer.getUserName(),
        //         newPlayer.getPassword());
        // Authentication auth = authManager.authenticate(authReq);

        // SecurityContext sc = SecurityContextHolder.getContext();
        // sc.setAuthentication(auth);
        // System.out.println(auth.isAuthenticated());
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