package com.example.letterboxed;

import com.example.letterboxed.classes.Player;
import com.example.letterboxed.services.PlayerService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class GameApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameApplication.class, args);
	}

	// @Bean
    // public CommandLineRunner demo(PlayerService playerService) {
    //     return (args) -> {

    //         //save a couple of players
    //         playerService.savePlayer(new Player("ala", "ala@ala.com", new BCryptPasswordEncoder().encode("ala")));
    //         playerService.savePlayer(new Player("mary", "mary@mary.com",  new BCryptPasswordEncoder().encode("mary")));
	// 		return;
    //     };
    // }

}
