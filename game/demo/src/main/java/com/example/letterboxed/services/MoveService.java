package com.example.letterboxed.services;


import com.example.letterboxed.classes.*;
import com.example.letterboxed.repository.MoveRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MoveService {

    private final MoveRepository moveRepository;


    @Autowired
    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    public Move createMove(Game game, Player player, Move moveDTO) {
        Move move = new Move();
        move.setWord(moveDTO.getWord());
        move.setPlayer(player);
        //move.setGame(optional);

        moveRepository.save(move);

        return move;
    }

    public boolean isPlayerTurn(Game game, Player firstPlayer, Player secondPlayer) {
        return GameLogic.playerTurn();
    }


}