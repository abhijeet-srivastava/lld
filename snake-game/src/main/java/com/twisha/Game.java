package com.twisha;

import java.util.List;
import java.util.Scanner;

public class Game {

    private Board board;


    public Game(int rows, int cols, int[] snakeHead, List<int[]> foodCells) {
        this.board = new Board(rows, cols, snakeHead, foodCells);
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean continueGame = true;
        while (continueGame) {
            System.out.printf("Enter direction to move:");
            char direction = scanner.next().charAt(0);
            if(!validMove(direction)) {
                System.out.printf("\nNot a valid direction, Make move as either of('L':Left, 'R':Right, 'U': Up, 'D': Down)\n");
                continue;
            }
            continueGame = board.nextMove(direction);
            if(continueGame) {
                this.board.printBoard();
            } else {
                System.out.printf("Game over, exiting");
            }
        }
    }

    private boolean validMove(char direction) {
        return direction != 'L' || direction != 'R' || direction != 'U' || direction != 'D';
    }
}
