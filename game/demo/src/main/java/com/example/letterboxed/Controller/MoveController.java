package com.example.letterboxed.Controller;

import com.example.letterboxed.DTO.MoveDTO;
import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Player;
import com.example.letterboxed.services.GameService;
import com.example.letterboxed.services.MoveService;
import com.example.letterboxed.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.management.InvalidAttributeValueException;
import javax.servlet.http.HttpSession;

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

    //Logger logger = LoggerFactory.getLogger(MoveController.class);

    @RequestMapping("")
    public String defaultMove() {
        return "move default path";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Game createMove(@RequestBody MoveDTO moveDTO) throws InvalidAttributeValueException {
        String gameId = (String) httpSession.getAttribute("gameId");
        // logger.info("move to insert:" + MoveDTO.getWord());

        //check if the move is valid
        Game game = gameService.getGame(gameId);
        Player currentplayer = moveDTO.player; 
        String word = moveDTO.word;
        
        Game updatedGame = moveService.createMove(game.getId(), currentplayer.getUserName(), word);
        if (updatedGame.getId() != null) {
            this.playerService.updatePlayerScore(currentplayer.getUserName());
        }
        return updatedGame;
    }

    @RequestMapping(value = "/turn", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isPlayerTurn() {
        String gameId = (String) httpSession.getAttribute("gameId");
        Game game = gameService.getGame(gameId);
        if (game.getGameStatus() == playerService.getLoggedUser().getUserName()) {
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/skipmove", method = RequestMethod.POST)
    public Game createMove(@RequestBody Player player) {
        return this.moveService.skipMove(player.getUserName());
    }
}