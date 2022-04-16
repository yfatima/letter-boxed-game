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

    public Player createNewPlayer(PlayerDTO newPlayerDTO) {
        Player newPlayer = new Player();
        newPlayer.setUserName(newPlayerDTO.getUserName());
        newPlayer.setPassword(passwordEncoder.encode(newPlayerDTO.getPassword()));
        newPlayer.setEmail(newPlayerDTO.getEmail());

        if (savePlayer(newPlayer)) {
            return newPlayer;
        }

        return new Player();
    }

    public Player getLoggedUser() {
        ContextUser principal = (ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Player> players = listPlayers();
        for (Player player : players) {
            if (principal.getPlayer().getUserName() == player.getUserName()) {
                return player;
            }
        }

        return new Player();
    }

    public List<Player> listPlayers() {
        List<Player> players = null;

        try {
            File file = new File("../data/players.json");
            ObjectMapper objectMapper = new ObjectMapper();
            players = objectMapper.readValue(file, new TypeReference<List<Player>>() {
            });
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return players;
    }

    public Player getPlayerByUsername(String username) {
        List<Player> players = listPlayers();
        for (Player player2 : players) {
            if (username == player2.getUserName()) {
                return player2;
            }
        }
        return new Player();
    }

    public boolean savePlayer(Player player) {
        try {
            File file = new File("/Users/yumnafatima/Desktop/ISA 681/letter-boxed-game/game/demo/src/main/java/com/example/letterboxed/data/players.json");
            FileWriter fileWriter = new FileWriter(file, true);

            ObjectMapper mapper = new ObjectMapper();

            SequenceWriter seqWriter = mapper.writer().writeValuesAsArray(fileWriter);
            seqWriter.write(player);
            seqWriter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getPlayerWordList(Player player) {
        List<String> wordlist = null;

        List<Player> players = listPlayers();
        for (Player player2 : players) {
            if (player.getUserName() == player2.getUserName()) {
                return player.getWordList();
            }
        }
        return wordlist;
    }

    public boolean addWord(String word, Player player) {
        List<Player> players = listPlayers();
        File file = new File("../data/players.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            for (Player player2 : players) {
                if (player.getUserName() == player2.getUserName()) {
                    player2.addWord(word);
                    objectMapper.writeValue(file, players);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
