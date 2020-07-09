package com.games.battleships.services.contracts;

import com.games.battleships.models.Square;

public interface IOService {

    byte[] serializeGameField(Square[][] field);

    Square[][] deserializeGameField(byte[] field);
}
