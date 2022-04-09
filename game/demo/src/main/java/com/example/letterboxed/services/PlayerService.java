package com.example.letterboxed.services;
import org.springframework.stereotype.Service;

import com.example.letterboxed.DTO.PlayerDTO;
import com.example.letterboxed.classes.Player;
import com.example.letterboxed.repository.PlayerRepository;
import com.example.security.ContextUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createNewPlayer(PlayerDTO newPlayerDTO) {
        Player newPlayer = new Player();
        newPlayer.setUserName(newPlayerDTO.getUserName());
        newPlayer.setPassword(passwordEncoder.encode(newPlayerDTO.getPassword()));
        newPlayer.setEmail(newPlayerDTO.getEmail());
        playerRepository.save(newPlayer);
        return newPlayer;
    }

    public Player getLoggedUser() {
        ContextUser principal = (ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return playerRepository.findOneByUserName(principal.getPlayer().getUserName());
    }

    public List<Player> listPlayers() {
        List<Player> players = (List<Player>) playerRepository.findAll();
        return players;
    }
}
