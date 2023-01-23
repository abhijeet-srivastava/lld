package com.test.assignments;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private int grid;
    private Map<Integer, Integer> snake = new HashMap<>();
    private Map<Integer,Integer> ladder = new HashMap<>();
    int winingPositon;
    public void addSnake(int head, int tail){
        snake.put(head,tail);
    }
    public void addLadder(int start, int end){
        ladder.put(start,end);
    }
    void setWiningPositon(int positon) {
        winingPositon =  positon;
    }
    void setGrid(int size) {
        grid = size;
    }
    public Board() {
        grid = 100;
        winingPositon = 100; // Can be set using setter
    }
    public void advanced(User user, int value){
        if(user.getPosition() + value > grid){
            return;
        }
        user.setPosition(user.getPosition() + value);
        if (snake.get(user.getPosition()) != null ){
            System.out.printf("User %s Bit By snake LOL!!\n", user.getName());
            user.setPosition(snake.get(user.getPosition()));
        }
        if (ladder.get(user.getPosition()) != null ){
            System.out.printf("User %s Got ladder Haha :)\n", user.getName());
            user.setPosition(ladder.get(user.getPosition()));
        }
    }
}
