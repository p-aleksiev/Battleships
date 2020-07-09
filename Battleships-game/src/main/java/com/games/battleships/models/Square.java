package com.games.battleships.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Square {
    private int value;

    @Override
    public String toString() {
        return super.toString();
    }

    public int[] indexOf(Square square, Square[][] matrix){

        int[] coordinates = new int[2];

        Outercycle:
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].equals(square)){
                    coordinates[0] = i;
                    coordinates[1] = j;
                    break Outercycle;
                }else {
                    coordinates[0] = -1;
                    coordinates[1] = -1;
                }
            }
        }
        return coordinates;
    }
}
