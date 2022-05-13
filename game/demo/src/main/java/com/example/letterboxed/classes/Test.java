package com.example.letterboxed.classes;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
    
    public static void main (String args[]) {
        List<Player> players = null;
        Player player = new Player("yfatima", "yf@gmu.edu", "hello");
        
        try {
            File file = new File("game/demo/src/main/java/com/example/letterboxed/data/players.json");
            ObjectMapper objectMapper = new ObjectMapper();
            players = objectMapper.readValue(file, new TypeReference<List<Player>>() {});
            //System.out.println(players.get(0).getUserName());
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        for (Player player2 : players) {
            if ("yfatima".equals(player2.getUserName())) {
                System.out.println(player2.getUserName());
                //player = player2;
                String pp = new BCryptPasswordEncoder().encode(player2.getPassword());
                System.out.println(pp);
            }
        }

        Game game = new Game();
        game.setP1Id(player.getUserName());
        game.setId("game1");
        game.setP2Id("none");
        game.setWinScore(3);
        game.setGameStatus("inactive");

        // find the game in games json file and add player 1 
        // then create a new json file for the game
        File file = new File("game/demo/src/main/java/com/example/letterboxed/data/games.json");
        File file2 = new File("game/demo/src/main/java/com/example/letterboxed/data/game.json");

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Game> games = objectMapper.readValue(file, new TypeReference<List<Game>>() {});
            System.out.println(games);
            for (Game game2 : games) {
                if (game.getId().equals(game2.getId())) {
                    game.setLetters(game2.getLetters());
                }
            }
            System.out.println(game.getLetters());
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        ObjectMapper objectMapper2 = new ObjectMapper();
        try {
            objectMapper2.writeValue(file2, game);
            System.out.println(game);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
