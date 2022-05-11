package com.example.letterboxed.Controller;

import com.example.letterboxed.DTO.MoveDTO;
import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Player;
import com.example.letterboxed.services.GameService;
import com.example.letterboxed.services.MoveService;
import com.example.letterboxed.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.management.InvalidAttributeValueException;
import javax.servlet.http.HttpSession;

/**
 * This MoveController class processes get and post requests made to move restful api.
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

    //Logger logger = LoggerFactory.getLogger(MoveController.class);

    /**
     * Method to process default get request to game default path
     * @return String msg
     */
    @RequestMapping("")
    public String defaultMove() {
        return "move default path";
    }

    /**
     * When 'move/create' restful api gets a post request, we get the game instance with game id and add the move word to it.
     * @param player1
     * @return Game game (updated)
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Game createMove(@RequestBody MoveDTO moveDTO) throws InvalidAttributeValueException {
        //get game id from http session 
        String gameId = (String) httpSession.getAttribute("gameId");
        // logger.info("move to insert:" + MoveDTO.getWord());

        //get the game with this game id 
        Game game = gameService.getGame(gameId);
        Player currentplayer = moveDTO.player; 
        String word = moveDTO.word;
        
        //add move (word) to the game object
        Game updatedGame = moveService.createMove(game.getId(), currentplayer.getUserName(), word);
        //if move was successful then update the score of the player who made the move
        if (updatedGame.getId() != null) {
            this.playerService.updatePlayerScore(currentplayer.getUserName());
        }
        return updatedGame;
    }

    // @RequestMapping(value = "/turn", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    // public boolean isPlayerTurn() {
    //     String gameId = (String) httpSession.getAttribute("gameId");
    //     Game game = gameService.getGame(gameId);
    //     if (game.getGameStatus() == playerService.getLoggedUser().getUserName()) {
    //         return true;
    //     }
    //     return false;
    // }

    /**
     * When 'move/skip' restful api gets a post request, we skip the turn of the given player and update gamestatus.
     * @param player
     * @return Game game (updated)
     */
    @RequestMapping(value = "/skipmove", method = RequestMethod.POST)
    public Game createMove(@RequestBody Player player) {
        return this.moveService.skipMove(player.getUserName());
    }
}