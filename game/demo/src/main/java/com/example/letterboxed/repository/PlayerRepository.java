package com.example.letterboxed.repository;

import com.example.letterboxed.classes.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, String> {
    Player findOneByUserName(String userName);
}