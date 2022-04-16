package com.example.letterboxed.security;

import com.example.letterboxed.classes.Player;
import com.example.letterboxed.services.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PlayerService playerService;

    @Autowired
    public UserDetailsServiceImpl(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        checkNotNull(username);

        Player player = playerService.getPlayerByUsername(username);
        if (player == null) {
            throw new UsernameNotFoundException("Player " + username + " doesn't exists");
        }
        return new ContextUser(player);
    }
}