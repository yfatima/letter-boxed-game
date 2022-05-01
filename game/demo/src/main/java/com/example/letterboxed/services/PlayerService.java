package com.example.letterboxed.services;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.example.letterboxed.DTO.PlayerDTO;
import com.example.letterboxed.classes.Player;
import com.example.letterboxed.security.ContextUser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PlayerService() {
    }

    public Boolean createNewPlayer(Player newPlayer) {
        // Player newPlayer = new Player();
        // newPlayer.setUserName(newPlayer.getUserName());
        newPlayer.setPassword(passwordEncoder.encode(newPlayer.getPassword()+newPlayer.getUserName()));
        // newPlayer.setEmail(newPlayerDTO.getEmail());

        if (savePlayer(newPlayer)) {
            return true;
        }

        return false;
    }

    public Player getLoggedUser() {
        ContextUser principal = (ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal.getPlayer().getUserName());

        List<Player> players = listPlayers();
        for (Player player : players) {
            if (principal.getPlayer().getUserName() == player.getUserName()) {
                return player;
            }
        }

        return new Player();
    }

    public Boolean authPlayer(Player player) {
        List<Player> players = listPlayers();
        
        for (Player p : players) {
            if (p.getUserName().equals(player.getUserName()) && passwordEncoder.matches(player.getPassword()+player.getUserName(), p.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public List<Player> listPlayers() {
        List<Player> players = null;

        try {
            File file = new File("game/demo/src/main/java/com/example/letterboxed/data/players.json");
            ObjectMapper objectMapper = new ObjectMapper();
            players = objectMapper.readValue(file, new TypeReference<List<Player>>() {});
            System.out.println(players);
            return players;
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return players;
    }

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

}
