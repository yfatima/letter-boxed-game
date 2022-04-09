package com.example.letterboxed.Controller;

import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Move;
import com.example.letterboxed.services.GameService;
import com.example.letterboxed.services.MoveService;
import com.example.letterboxed.services.PlayerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Created by pdybka on 24.06.16.
 */
@RestController
@RequestMapping("/move")
public class MoveController {

    @Autowired
    private MoveService moveService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @Autowired
    private HttpSession httpSession;

    Logger logger = LoggerFactory.getLogger(MoveController.class);

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Move createMove(@RequestBody Move MoveDTO) {
        String gameId = (String) httpSession.getAttribute("gameId");
        logger.info("move to insert:" + MoveDTO.getWord());


        Move move = moveService.createMove(gameService.getGame(gameId), playerService.getLoggedUser(), MoveDTO);
        Game game = gameService.getGame(gameId);
        //gameService.updateGameStatus(gameService.getGame(gameId), moveService.checkCurrentGameStatus(game));

        return move;
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public boolean validateMoves() {
        String gameId = (String) httpSession.getAttribute("gameId");
        return false;
    }

    @RequestMapping(value = "/turn", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isPlayerTurn() {
        String gameId = (String) httpSession.getAttribute("gameId");
        return false;
    }
}