package com.games.battleships.services.contracts;

import com.games.battleships.models.BattleshipsGame;
import com.games.battleships.models.Square;

public interface BattleshipsService {

    Square[][] arrangeShips(int[] shipSizes);

    void computerTurn(String gameId);

    void playerTurn(String gameId, int[] coordinates);

    Square[][] createEmptyField();

    BattleshipsGame createBattleshipsGame();

    BattleshipsGame getGameById(String id);

}
