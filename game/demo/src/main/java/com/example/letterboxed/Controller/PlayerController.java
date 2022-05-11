package com.example.letterboxed.Controller;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
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

/**
 * This PlayerController class processes get and post requests made to player restful api.
 */
@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

      /**
     * Method to process default get request to player default path
     * @return String msg
     */
    @RequestMapping("")
    public String defaultPlayer() {
        return "player default path";
    }

    /**
     * When 'player/create' restful api gets a post request, we create an instance of player object for this new user
     * given the user information by the frontend.
     * @param newPlayer
     * @return Boolean
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Boolean createAccount(@RequestBody Player newPlayer) {
        //System.out.println(newPlayer);
        return playerService.createNewPlayer(newPlayer);
    }

  
    // @RequestMapping(value = "/logged", method = RequestMethod.GET, produces =MediaType.APPLICATION_JSON_VALUE)
    // public Player getLoggedPlayer() {
    //     return playerService.getLoggedUser();
    // }

    /**
     * When 'player/login' restful api gets a post request, we authenticate the given player and return if the player can
     * login to the application or not.
     * @param newPlayer
     * @return Boolean
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Boolean authPlayer(@RequestBody Player newPlayer) {
        return playerService.authPlayer(newPlayer);
    }

    /**
     * When 'player/getplayer/{username}/{hashvalue}' restful api gets a get request, we return a player by the given 
     * username if it's not changed during transmission using the given hashvalue as well.
     * @param username
     * @param hashvalue
     * @return Player player
     */
    @RequestMapping(value = "/getplayer/{username}/{hashvalue}", method = RequestMethod.GET)
    public Player getPlayerByUsername(@PathVariable String username, @PathVariable String hashvalue) {
        // System.out.println(hashvalue);
        try {
            if (!checkIntegrity(username, hashvalue)) {
                return new Player();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return playerService.getPlayerByUsername(username);
    }

    /**
     * When 'player/getplayerscore/{username}/{hashvalue}' restful api gets a get request, we return player's score if
     * the username is not changed in transmission using the given hashvalue.
     * @param username
     * @param hashvalue
     * @return Integer playescore
     */
    @RequestMapping(value = "/getplayerscore/{username}/{hashvalue}", method = RequestMethod.GET)
    public Integer getPlayerScore(@PathVariable String username, @PathVariable String hashvalue) {
        try {
            if (!checkIntegrity(username, hashvalue)) {
                return -1;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return playerService.getPlayerScore(username);
    }

    // @RequestMapping(value = "/clearplayerscore", method = RequestMethod.POST)
    // public Integer clearPlayerScore(@PathVariable String username) {
    //     return playerService.clearPlayerScore(username);
    // }

    /**
     * When 'player/players' restful api gets a get request, we return the list of players who have registered 
     * to our game application.
     * @return List<Player> players
     */
    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public List<Player> getPlayers() {
        return playerService.listPlayers();
    }


    /**
     * This method performs the integrity check on the username of a player. We match the original hash vale with
     * the new hash value generated with the username that we got.
     * @param username
     * @param originalhashvalue
     * @return  Boolean
     * @throws Exception
     */
    public Boolean checkIntegrity(String username, String originalhashvalue)  throws Exception {

        ByteArrayOutputStream byte_Stream = new ByteArrayOutputStream();
        //change string to byte array
        byte_Stream.write(username.getBytes());
        byte[] valueToHash = byte_Stream.toByteArray();
        //compute sha256 hash value of the username
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        String testhashvalue = DatatypeConverter.printHexBinary(messageDigest.digest(valueToHash)).toLowerCase();
        //compare hash values and return result
        if (testhashvalue.equals(originalhashvalue)) {
            return true;
        }
        return false;
    }


}