package com.games.battleships.services;

import com.games.battleships.models.BattleshipsGame;
import com.games.battleships.models.Square;
import com.games.battleships.repositories.BattleshipsRepository;
import com.games.battleships.services.contracts.BattleshipsService;
import com.games.battleships.services.contracts.IOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.games.battleships.Constants.SHIP_SIZES;


@Service
public class BattleshipsServiceImpl implements BattleshipsService {

    private final BattleshipsRepository battleshipsRepository;
    private final IOService ioService;

    @Autowired
    public BattleshipsServiceImpl(BattleshipsRepository battleshipsRepository, IOService ioService) {
        this.battleshipsRepository = battleshipsRepository;
        this.ioService = ioService;
    }

    @Override
    public void computerTurn(String gameId) {

        BattleshipsGame game = battleshipsRepository.getById(gameId);
        Square[][] playerField = ioService.deserializeGameField(game.getPlayerField());

        while (true){
            int x = (int) (Math.random() * 10);
            int y = (int) (Math.random() * 10);

            if(playerField[x][y].getValue() == 0){
                playerField[x][y].setValue(2);
                break;
            }

            if(playerField[x][y].getValue() == 1){
                playerField[x][y].setValue(3);
                break;
            }
        }

        game.setPlayerField(ioService.serializeGameField(playerField));
        battleshipsRepository.saveAndFlush(game);
    }

    @Override
    public void playerTurn(String gameId, int[] coordinates) {

        BattleshipsGame game = battleshipsRepository.getById(gameId);
        Square[][] computerField = ioService.deserializeGameField(game.getComputerField());
        Square[][] visualField = ioService.deserializeGameField(game.getVisualField());


        if(computerField[coordinates[0]][coordinates[1]].getValue() == 0){
            visualField[coordinates[0]][coordinates[1]].setValue(2);
        }else {
            visualField[coordinates[0]][coordinates[1]].setValue(3);
        }

        game.setVisualField(ioService.serializeGameField(visualField));
        battleshipsRepository.saveAndFlush(game);
    }

    @Override
    public Square[][] createEmptyField() {
        Square[][] matrix = new Square[10][10];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = new Square(0,i,j);
            }
        }
        return matrix;
    }

    @Override
    public BattleshipsGame createBattleshipsGame() {
        BattleshipsGame game = new BattleshipsGame();

        Square[][] playerField = this.arrangeShips(SHIP_SIZES);
        Square[][] computerField = this.arrangeShips(SHIP_SIZES);
        Square[][] visualField = this.createEmptyField();

        game.setPlayerField(ioService.serializeGameField(playerField));
        game.setComputerField(ioService.serializeGameField(computerField));
        game.setVisualField(ioService.serializeGameField(visualField));

        battleshipsRepository.saveAndFlush(game);
        return game;
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
                        shipPlaced = this.placeUp(matrix, shipSize, row, col);
                        break;
                    case 1:
                        shipPlaced = this.placeDown(matrix, shipSize, row, col);
                        break;
                    case 2:
                        shipPlaced = this.placeLeft(matrix, shipSize, row, col);
                        break;
                    case 3:
                        shipPlaced = this.placeRight(matrix, shipSize, row, col);
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
