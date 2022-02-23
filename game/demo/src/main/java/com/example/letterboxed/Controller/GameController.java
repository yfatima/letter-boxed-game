package com.example.letterboxed.Controller;

import com.example.letterboxed.classes.Game;
import com.example.letterboxed.services.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class GameController {
    
    @Autowired
    GameService gameService;
	
    @RequestMapping("/gameinfo")
    public Game listUsers() {
        System.out.println("getting game info in controller");
        return gameService.getG1();
    }
}
