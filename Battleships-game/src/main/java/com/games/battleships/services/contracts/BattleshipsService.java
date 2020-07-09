package com.games.battleships.services.contracts;

import com.games.battleships.models.BattleshipsGame;
import com.games.battleships.models.Square;

public interface BattleshipsService {

    Square[][] arrangeShips(int[] shipSizes);

    int[] computerTurn();

    Square[][] createEmptyField();

    String createBattleshipsGame (BattleshipsGame battleshipsGame);

    BattleshipsGame getGameById(String id);
}
