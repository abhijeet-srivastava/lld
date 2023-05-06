package com.test.assignments;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        //main.testOneShotSchedule();
        //main.testFixedRate();
        //main.testFixedDelay();
    }

    private void testFixedRate() {
        ScheduledExecutorService scheduledExecutorService = new ScheduledExecutorService(2);
        scheduledExecutorService.scheduleAtFixedRate(new Task("here10s repeat 0 sec delay"), 0, 10, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(new Task("here10s repeat 5 sec delay"), 5, 10, TimeUnit.SECONDS);
    }

    private void testFixedDelay() {
        ScheduledExecutorService scheduledExecutorService = new ScheduledExecutorService(2);
        scheduledExecutorService.scheduleWithFixedDelay(new Task("here10s delay 0 sec initdelay"), 0, 10, TimeUnit.SECONDS);
    }


    private void testOneShot() {
        CustomThreadPoolExecutor customThreadPoolExecutor = new CustomThreadPoolExecutor(2);
        customThreadPoolExecutor.execute(new Task("here1"));
        customThreadPoolExecutor.execute(new Task("here2"));
        customThreadPoolExecutor.execute(new Task("here3"));
        customThreadPoolExecutor.execute(new Task("here4"));
        customThreadPoolExecutor.execute(new Task("here5"));
        customThreadPoolExecutor.execute(new Task("here6"));
    }

    private void testOneShotSchedule() {
        ScheduledExecutorService scheduledExecutorService = new ScheduledExecutorService(2);
        scheduledExecutorService.schedule(new Task("here0s delay"), 0, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(new Task("here10s delay"), 10, TimeUnit.SECONDS);
    }

    class Task implements Runnable {
        private final String message;

        public Task(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            System.out.printf("%s\n", message);
            try {
                System.out.printf("Wait started\n");
                Thread.sleep(10000);
                System.out.printf("Wait ended\n");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}