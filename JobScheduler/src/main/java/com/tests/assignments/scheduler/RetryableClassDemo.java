package com.tests.assignments.scheduler;

public class RetryableClassDemo {

    public static void main(String[] args) {
        JobScheduler scheduler = new JobScheduler();
        for(int i = 0; i < 100; i++) {
            int finalI = i;
            Runnable runnable = () -> {
                try {
                    Thread.sleep(1000);
                    System.out.printf("Executing task: %d\n", finalI);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };
            scheduler.submit(runnable);
        }
    }
}
