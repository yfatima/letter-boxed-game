package com.example.letterboxed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

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
