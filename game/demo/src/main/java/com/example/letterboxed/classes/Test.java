package com.example.letterboxed.classes;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
    
    public static void main (String args[]) {
        List<Player> players = null;
        
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
                String pp = new BCryptPasswordEncoder().encode(player2.getPassword());
                System.out.println(pp);
            }
        }

    }
}
