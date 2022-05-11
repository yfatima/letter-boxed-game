package com.example.letterboxed.services;

import java.io.File;
import java.util.List;

import com.example.letterboxed.classes.Player;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This PlayerService class methods are called by the PlayerController to do backend data processing and logical work.
 */
@Service
public class PlayerService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PlayerService() {
    }

    /**
     * This method creates the new player from the given information.
     * @param newPlayer
     * @return boolean
     */
    public boolean createNewPlayer(Player newPlayer) {
        newPlayer.setPassword(passwordEncoder.encode(newPlayer.getPassword()+newPlayer.getUserName()));

        if (savePlayer(newPlayer)) {
            return true;
        }
        return false;
    }

    // public Player getLoggedUser() {
    //     ContextUser principal = (ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     // System.out.println(principal.getPlayer().getUserName());

    //     List<Player> players = listPlayers();
    //     for (Player player : players) {
    //         if (principal.getPlayer().getUserName() == player.getUserName()) {
    //             return player;
    //         }
    //     }
    //     return new Player();
    // }

    /**
     * This method authenticates the player that they have registered before and gave valid username and password.
     * @param player
     * @return boolean
     */
    public boolean authPlayer(Player player) {
        List<Player> players = listPlayers();
        
        for (Player p : players) {
            if (p.getUserName().equals(player.getUserName()) && passwordEncoder.matches(player.getPassword()+player.getUserName(), p.getPassword())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns the list of all registered players from our players json data file.
     * @return List<Player> players
     */
    public List<Player> listPlayers() {
        List<Player> players = null;

        try {
            File file = new File("game/demo/src/main/java/com/example/letterboxed/data/players.json");
            ObjectMapper objectMapper = new ObjectMapper();
            players = objectMapper.readValue(file, new TypeReference<List<Player>>() {});
            return players;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return players;
    }

    /**
     * This method returns the player associated the given username.
     * @param username
     * @return Player player
     */
    public Player getPlayerByUsername(String username) {
        List<Player> players = listPlayers();
        for (Player player2 : players) {
            if (username.equals(player2.getUserName())) {
                System.out.println(player2.getUserName());
                return player2;
            }
        }
        return new Player();
    }

    /**
     * This method saves the player in the players json data file.
     * @param player
     * @return boolean
     */
    public boolean savePlayer(Player player) {

        List<Player> players = listPlayers();
        players.add(player);
        try {
            File file = new File("game/demo/src/main/java/com/example/letterboxed/data/players.json");
            ObjectMapper mapper = new ObjectMapper();
            
            mapper.writeValue(file, players);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method updates the player score by increment of 1 given the player username.
     * @param username
     * @return int player score (updated)
     */
    public int updatePlayerScore(String username) {
        int newScore = 0;
        List<Player> players = listPlayers();
        for (Player player2 : players) {
            if (username.equals(player2.getUserName())) {
                player2.setScore(player2.getScore()+1);
            }
        }

        try {
            File file = new File("game/demo/src/main/java/com/example/letterboxed/data/players.json");
            ObjectMapper mapper = new ObjectMapper();
            
            mapper.writeValue(file, players);
            return newScore;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newScore;
    }

    /**
     * This method gets the player's score given their username.
     * @param username
     * @return int player score
     */
    public int getPlayerScore(String username) {
        int score = 0;
        // System.out.println("in get score");
        List<Player> players = listPlayers();
        for (Player player2 : players) {
            if (username.equals(player2.getUserName())) {
                score = player2.getScore();
            }
        }
        return score;
    }

    /**
     * This method clears the player score from player object in the players json data file after game is complete.
     * @param username
     * @return int player score (updated)
     */
    public int clearPlayerScore(String username) {
        int newScore = -1;
        List<Player> players = listPlayers();
        for (Player player2 : players) {
            if (username.equals(player2.getUserName())) {
                player2.setScore(0);
            }
        }
        try {
            File file = new File("game/demo/src/main/java/com/example/letterboxed/data/players.json");
            ObjectMapper mapper = new ObjectMapper();
            
            mapper.writeValue(file, players);
            return newScore;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newScore;
    }

}
