package com.games.battleships.repositories;

import com.games.battleships.models.BattleshipsGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleshipsRepository extends JpaRepository<BattleshipsGame, String> {

    BattleshipsGame getById(String id);
}
