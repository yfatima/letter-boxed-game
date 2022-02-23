package com.example.letterboxed.services;

import java.util.Arrays;
import java.util.List;

import com.example.letterboxed.classes.Game;

import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Game g1 = new Game("letter-boxed", 5, 5, Arrays.asList("i", "u", "w", "q"));

    public Game getG1() {
        return g1;
    }
}

