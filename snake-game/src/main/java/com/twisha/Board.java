package com.twisha;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Board {
    private int[][] board;
    private List<int[]> foodCells;

    private int foodIndex = 0;

    private Snake snake;

    private final Map<Character, int[]> DIRECTIONS = Map.of(
            'L', new int[]{0, 1},
            'R', new int[]{0, -1},
            'U', new int[]{-1, 0},
            'D', new int[]{1, 0}
    );

    public Board(int rows, int cols, int[] snakeHead, List<int[]> foodCells) {
        this.board = new int[rows][cols];
        this.foodCells = new LinkedList<>(foodCells);
        this.snake = new Snake(snakeHead);
    }

    public boolean nextMove(char direction) {
        if(!DIRECTIONS.containsKey(direction)) {
            throw new IllegalArgumentException("Invalid direction");
        }
        int[] dir = DIRECTIONS.get(direction);
        int[] currHead = this.snake.getHead();
        int[] nextCell = {currHead[0] + dir[0], currHead[1] + dir[1]};
        if(nextCell[0] < 0 || nextCell[0] >= this.board.length
                || nextCell[1] < 0 || nextCell[1] >= this.board[0].length) {
            return false;
        }
        boolean isFood = isFood(nextCell);
        if(isFood) {
            this.foodIndex += 1;
        }
        return this.snake.moveSnake(nextCell, isFood);
    }

    private boolean isFood(int[] nextCell) {
        int[] foundFoodCell = null;
        for (int[] foodCell : this.foodCells) {
            if(foodCell[0] == nextCell[0] && foodCell[1] == nextCell[1]) {
                foundFoodCell = foodCell;
                break;
            }
        }

        if(foundFoodCell != null) {
            foodCells.remove(foundFoodCell);
            return true;
        }
        /*boolean isFood = this.foodIndex < this.foodCells.size()
                && nextCell[0] == this.foodCells.get(foodIndex)[0]
                && nextCell[1] == this.foodCells.get(foodIndex)[1];*/
        return false;
    }

    public void printBoard() {
        this.snake.printSnake();
    }
}
