package com.tests.assignments.scheduler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class JobScheduler {
    private ExecutorService taskExecutor;
    private ConcurrentLinkedQueue<RetryableTask> taskQueue;

    private final AtomicInteger taskIdGen = new AtomicInteger();

    private static final Integer MAX_RETRY = 3;

    public JobScheduler() {
        this.taskExecutor = Executors.newFixedThreadPool(10);
        this.taskQueue = new ConcurrentLinkedQueue<>();

        new Thread(this::startScheduler).start();
    }

    public void submit(Runnable runnable) {
        int id = taskIdGen.incrementAndGet();
        this.taskQueue.offer(new RetryableTask(runnable, id, this));
    }

    private void startScheduler() {
        new Thread(() -> {
            while(true) {
                RetryableTask nextTask = this.taskQueue.poll();
                if (nextTask != null) {
                    taskExecutor.submit(nextTask);
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

    public void shutdown() {
        System.out.println("Shutting down scheduler");
        taskExecutor.shutdown();
        try {
            taskExecutor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public class RetryableTask implements Runnable {

        private Runnable runnable;
        private AtomicInteger retryCount;

        private final int id; // For logging
        private final JobScheduler scheduler;

        public RetryableTask(Runnable runnable,  int id, JobScheduler scheduler) {
            this.runnable = runnable;
            this.id = id;
            this.scheduler = scheduler;
            this.retryCount = new AtomicInteger(0);
        }

        @Override
        public void run() {
            try {
                System.out.printf("Task %d: Attempt %d\n", id, retryCount.get() + 1);

                // Simulate random failure
                if (Math.random() < 0.3) { // 30% failure rate
                    throw new RuntimeException("Simulated failure for " + id);
                }

                runnable.run();
                System.out.printf("Task %d: Completed successfully\n", id);
            } catch (Exception e) {
                int currentRetry = retryCount.incrementAndGet();
                System.out.printf("Task %d: Failed attempt %d - %s\n", id, currentRetry, e.getMessage());

                if (currentRetry < MAX_RETRY) {
                    scheduler.taskQueue.offer(this); // Retry
                    System.out.printf("Task %d: Retrying...\n", id);
                } else {
                    System.out.printf("Task %d: Failed permanently after %d attempts\n", id, currentRetry);
                }
            }
        }
    }

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
        try {
            Thread.sleep(40*1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //scheduler.shutdown();
    }

}
