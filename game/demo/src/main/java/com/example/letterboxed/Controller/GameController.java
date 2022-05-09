package com.example.letterboxed.Controller;
import com.example.letterboxed.DTO.PlayerDTO;
import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Player;
import com.example.letterboxed.services.GameService;
import com.example.letterboxed.services.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    PlayerService playerService;

    @Autowired
    HttpSession httpSession;

    Logger logger = LoggerFactory.getLogger(GameController.class);

    @RequestMapping("")
    public String defaultGame() {
        return "game default path";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Game createGame(@RequestBody Player player1) {
        //System.out.println(player1);
        Game game = gameService.createNewGame(player1);
        this.playerService.clearPlayerScore(player1.getUserName());
        httpSession.setAttribute("gameId", game.getId());
        logger.info("new game id: " + httpSession.getAttribute("gameId")+ " stored in session" );

        return game;
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public Game joinGame(@RequestBody PlayerDTO playerdto) {
        Game game;
        if (playerdto.gameId.matches("[0-9]+")) {
            this.playerService.clearPlayerScore(playerdto.player.getUserName());
            game = gameService.joinGame(playerdto.player, playerdto.gameId);
            return game;
        }
        return new Game();
    }


    @RequestMapping(value = "/list")
    public List<Game> getGames() {
        return gameService.getGames();
    }

    // @RequestMapping(value = "/{id}")
    // public Game getGameProperties(@PathVariable String id) {
    //     httpSession.setAttribute("gameId", id);
    //     return gameService.getGame(id);
    // }

    @RequestMapping(value = "/gamestatus", method = RequestMethod.POST)
    public Game getGameStatus(@RequestBody String id) {
        //System.out.println(id);
        httpSession.setAttribute("gameId", id);
        Game result = gameService.getGame(id);
        //System.out.println(result);
        return result;
    }

    @RequestMapping(value = "/updategamestatus", method = RequestMethod.POST)
    public Boolean updateGameStatus(@RequestBody PlayerDTO playerdto) {
        //System.out.println(playerdto);
        return gameService.updateGameStatus(playerdto.player.getUserName(), playerdto.gameId);
    }



}
