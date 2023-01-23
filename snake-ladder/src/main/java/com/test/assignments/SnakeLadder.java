package com.test.assignments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeLadder {
    public Board board;
    private List<User> users = new ArrayList<>();
    private List<User> winnerList = new ArrayList<>();
    public void addUser(User newUser) {
        users.add(newUser);
    }

    public SnakeLadder(Board board, List<User> userList) {
        this.board = board;
        this.users = userList;
    }

    public int rollDice() {
        Random r = new Random();
        int value = r.nextInt(6) + 1;
        if (value == 6) {
            value += r.nextInt(6) + 1;
        }
        if (value == 12) {
            value += r.nextInt(6) + 1;
        }
        if (value == 18) {
            return 0;
        }
        return value;
    }

    public void simulate() {
        int current = 0;
        int ite = 0;
        while (winnerList.size() < users.size()) {
            if (current >= users.size() - 1) {
                current = 0;
            } else {
                current += 1;
            }
            //System.out.println(current);
            User currUser = users.get(current);
            if (currUser.getPosition() == 100) {
                continue;
            }
            int rollValue = rollDice();
            board.advanced(currUser, rollValue);
            if (currUser.getPosition() == board.winingPositon) {
                winnerList.add(currUser);
                System.out.printf(" We have a total %d  winners, current winner: %s\n", winnerList.size(), currUser.getName());
            }
            for (User user : users) {
                System.out.printf("UserName  %s is on current Position :%d\n", user.getName(), user.getPosition());
            }
            ite += 1;
        }
        System.out.printf(" Simulation finished Total Iteration %d\n", ite);
    }
}
