package com.twisha;

import java.util.ArrayDeque;
import java.util.Deque;

public class Snake {
    private Deque<int[]> snake;

    public Snake(int[] head) {
        this.snake = new ArrayDeque<>();
        snake.offerFirst(head);
    }

    public boolean moveSnake(int[] nextCell, boolean isFood) {
        for(int[] body: this.snake) {
            if(body[0] == nextCell[0] && body[1] == nextCell[1]) {
                return false;
            }
        }
        snake.offerFirst(nextCell);
        if (!isFood) {
            snake.pollLast();
        }
        return true;
    }

    public int[] getHead() {
        return this.snake.peekFirst();
    }

    public void printSnake() {
        this.snake.forEach(cell -> System.out.printf("(%d,%d):", cell[0], cell[1]));
        System.out.printf("\n");
    }
}
