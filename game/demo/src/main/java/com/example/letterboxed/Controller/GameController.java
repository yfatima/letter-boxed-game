package com.example.letterboxed.Controller;
import com.example.letterboxed.DTO.PlayerDTO;
import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Player;
import com.example.letterboxed.services.GameService;
import com.example.letterboxed.services.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This GameController class processes get and post requests made to game restful api.
 */
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

    /**
     * Method to process default get request to game default path
     * @return String msg
     */
    @RequestMapping("")
    public String defaultGame() {
        return "game default path";
    }

    /**
     * When 'game/create' restful api gets a post request, we create a game object instance with player 1 information 
     * from the frontend.
     * @param player1
     * @return Game game
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Game createGame(@RequestBody Player player1) {
        //System.out.println(player1);
        Game game = gameService.createNewGame(player1);
        if (this.playerService.clearPlayerScore(player1.getUserName()) != -1) {
            httpSession.setAttribute("gameId", game.getId());
            logger.info("new game id: " + httpSession.getAttribute("gameId")+ " stored in session" );
            return game;
        }
        return new Game();
        
    }

    /**
     * When 'game/join' restful api gets a post request, we add player 2 to the game that has already started.
     * We check again if game id is only numbers before processing it.
     * @param playerdto
     * @return Game game (updated)
     */
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public Game joinGame(@RequestBody PlayerDTO playerdto) {
        Game game;
        System.out.println(playerdto.gameId);
        if (checkGameId(playerdto.gameId)) {
            if (this.playerService.clearPlayerScore(playerdto.player.getUserName()) != -1) {
                game = gameService.joinGame(playerdto.player, playerdto.gameId);
                return game;
            }
            
        }
        System.out.println("RE no match\n");
        return new Game();
    }

    /**
     * When 'game/list' restful api gets a get request, we return the list of games already completed to the frontend.
     * @return List<Game> games
     */
    @RequestMapping(value = "/list")
    public List<Game> getGames() {
        return gameService.getGames();
    }

    /**
     * When 'game/gamestatus' restful api gets a get request, we return the game object that has been updated using
     * game id.
     * @param id
     * @return Game game
     */
    @RequestMapping(value = "/gamestatus/{id}", method = RequestMethod.GET)
    public Game getGameStatus(@PathVariable String id) {
        httpSession.setAttribute("gameId", id);
        if (checkGameId(id)){
            Game result = gameService.getGame(id);
            return result;
        }
        //System.out.println(result);
        return new Game();
    }

    /**
     * When 'game/updategamestatus' restful api gets a post request, we update whose turn is it in the game using 
     * game id and player username.
     * @param playerdto
     * @return Boolean 
     */
    @RequestMapping(value = "/updategamestatus", method = RequestMethod.POST)
    public boolean updateGameStatus(@RequestBody PlayerDTO playerdto) {
        //System.out.println(playerdto);
        if (checkGameId(playerdto.gameId)) {
            return gameService.updateGameStatus(playerdto.player.getUserName(), playerdto.gameId);
        }
        return false;
    }

    /**
     * This method checks if game id is valid meaning one or more numbers
     * @param gameId
     * @return boolean
     */
    private boolean checkGameId(String gameId) {
        return gameId.matches("[0-9]+");
    }


}
