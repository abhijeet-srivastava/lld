package com.test.assignments;

import com.test.assignments.service.ElevatorManager;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");
        Main main = new Main();
        main.testElevator();
    }

    private void testElevator() throws InterruptedException {
        ElevatorManager controller = new ElevatorManager(3);

        controller.requestElevator(5, 10);
        Thread.sleep(1500);
        controller.requestElevator(3, 7);
        Thread.sleep(1000);
        controller.requestElevator(8, 2);
        Thread.sleep(1200);
        controller.requestElevator(1, 9);
    }
}