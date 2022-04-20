package com.example.letterboxed.Controller;

import com.example.letterboxed.classes.Game;
import com.example.letterboxed.classes.Move;
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

import java.util.List;

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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Move createMove(@RequestBody Move MoveDTO) {
        String gameId = (String) httpSession.getAttribute("gameId");
        // logger.info("move to insert:" + MoveDTO.getWord());

        //check if the move is valid
        Game game = gameService.getGame(gameId);
        Player currentplayer = playerService.getLoggedUser(); 
        Player otherplayer;

        if (game.getP1Id() == currentplayer.getUserName()) {
            otherplayer = playerService.getPlayerByUsername(game.getP2Id());
        } else {
            otherplayer = playerService.getPlayerByUsername(game.getP1Id());
        }
        List<String> curentplayer_wordlist = currentplayer.getWordList();
        List<String> otherplayer_wordlist = otherplayer.getWordList();

        String word = MoveDTO.getWord();
        if (otherplayer_wordlist.contains(word) || curentplayer_wordlist.contains(word)) {
            return new Move();
        }

        //add it to current user word list
        //update game status to next player's id
        if (!playerService.addWord(word, currentplayer) || !gameService.updateGameStatus(otherplayer.getUserName(), gameId)) {
            return new Move();
        }
        //lastly create move and add it to move json file

        Move move = moveService.createMove(game.getId(), playerService.getLoggedUser().getUserName(), MoveDTO);
        return move;
    }

    // @RequestMapping(value = "/check", method = RequestMethod.GET)
    // public boolean validateMoves() {
    //     String gameId = (String) httpSession.getAttribute("gameId");
    //     return false;
    // }

    @RequestMapping(value = "/turn", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isPlayerTurn() {
        String gameId = (String) httpSession.getAttribute("gameId");
        Game game = gameService.getGame(gameId);
        if (game.getGameStatus() == playerService.getLoggedUser().getUserName()) {
            return true;
        }
        return false;
    }
}