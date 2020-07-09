package com.games.battleships.services;

import com.games.battleships.models.BattleshipsGame;
import com.games.battleships.models.Square;
import com.games.battleships.repositories.BattleshipsRepository;
import com.games.battleships.services.contracts.BattleshipsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BattleshipsServiceImpl implements BattleshipsService {

    private final BattleshipsRepository battleshipsRepository;

    @Autowired
    public BattleshipsServiceImpl(BattleshipsRepository battleshipsRepository) {
        this.battleshipsRepository = battleshipsRepository;
    }

    @Override
    public int[] computerTurn() {
        return new int[]{
                (int) (Math.random() * 10),
                (int) (Math.random() * 10)
        };
    }

    @Override
    public Square[][] createEmptyField() {
        Square[][] matrix = new Square[10][10];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = new Square(0);
            }
        }
        return matrix;
    }

    @Override
    public String createBattleshipsGame(BattleshipsGame battleshipsGame) {
        battleshipsRepository.saveAndFlush(battleshipsGame);
        return battleshipsGame.getId();
    }

    @Override
    public BattleshipsGame getGameById(String id) {
        return battleshipsRepository.getById(id);
    }

    @Override
    public Square[][] arrangeShips(int[] shipSizes) {
        Square[][] matrix = createEmptyField();

        for (int shipSize : shipSizes) {
            boolean shipPlaced = false;

            while (!shipPlaced) {
                int row = (int) (Math.random() * 10);
                int col = (int) (Math.random() * 10);
                int upDownLeftOrRight = (int) (Math.random() * 4);

                switch (upDownLeftOrRight) {
                    case 0:
                        shipPlaced = placeUp(matrix, shipSize, row, col);
                        break;
                    case 1:
                        shipPlaced = placeDown(matrix, shipSize, row, col);
                        break;
                    case 2:
                        shipPlaced = placeLeft(matrix, shipSize, row, col);
                        break;
                    case 3:
                        shipPlaced = placeRight(matrix, shipSize, row, col);
                        break;
                }
            }
        }

        return matrix;
    }

    private boolean placeUp(Square[][] matrix, int shipSize, int row, int col) {
        boolean canBeUp = false;

        for (int i = 0; i < shipSize; i++) {
            if (row - i < 0 || matrix[row - i][col].getValue() == 1 ||
                    row + 1 >= matrix.length || matrix[row + 1][col].getValue() == 1 ||
                    col + 1 >= matrix[0].length || matrix[row - i][col + 1].getValue() == 1 ||
                    col - 1 < 0 || matrix[row - i][col - 1].getValue() == 1) {
                canBeUp = false;
                break;
            }
            canBeUp = true;
        }

        if (canBeUp) {
            for (int i = 0; i < shipSize; i++) {
                matrix[row - i][col].setValue(1);
            }
        }

        return canBeUp;
    }

    private boolean placeDown(Square[][] matrix, int shipSize, int row, int col) {
        boolean canBeDown = false;

        for (int i = 0; i < shipSize; i++) {
            if (row + i >= matrix.length || matrix[row + i][col].getValue() == 1 ||
                    row - 1 < 0 || matrix[row - 1][col].getValue() == 1 ||
                    col + 1 >= matrix[0].length || matrix[row + i][col + 1].getValue() == 1 ||
                    col - 1 < 0 || matrix[row + i][col - 1].getValue() == 1) {
                canBeDown = false;
                break;
            }
            canBeDown = true;
        }

        if (canBeDown) {
            for (int i = 0; i < shipSize; i++) {
                matrix[row + i][col].setValue(1);
            }
        }

        return canBeDown;
    }

    private boolean placeLeft(Square[][] matrix, int shipSize, int row, int col) {
        boolean canBeLeft = false;

        for (int i = 0; i < shipSize; i++) {
            if (col - i < 0 || matrix[row][col - i].getValue() == 1 ||
                    col + 1 >= matrix[0].length || matrix[row][col + 1].getValue() == 1 ||
                    row + 1 >= matrix.length || matrix[row + 1][col - i].getValue() == 1 ||
                    row - 1 < 0 || matrix[row - 1][col - i].getValue() == 1) {
                canBeLeft = false;
                break;
            }
            canBeLeft = true;
        }

        if (canBeLeft) {
            for (int i = 0; i < shipSize; i++) {
                matrix[row][col - i].setValue(1);
            }
        }

        return canBeLeft;
    }

    private boolean placeRight(Square[][] matrix, int shipSize, int row, int col) {
        boolean canBeRight = false;

        for (int i = 0; i < shipSize; i++) {
            if (col + i >= matrix[0].length || matrix[row][col + i].getValue() == 1 ||
                    col - 1 < 0 || matrix[row][col - 1].getValue() == 1 ||
                    row + 1 >= matrix.length || matrix[row + 1][col + i].getValue() == 1 ||
                    row - 1 < 0 || matrix[row - 1][col + i].getValue() == 1) {
                canBeRight = false;
                break;
            }
            canBeRight = true;
        }

        if (canBeRight) {
            for (int i = 0; i < shipSize; i++) {
                matrix[row][col + i].setValue(1);
            }
        }

        return canBeRight;
    }


}
