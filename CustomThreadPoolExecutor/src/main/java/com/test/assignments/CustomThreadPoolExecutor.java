package com.test.assignments;

import java.util.ArrayList;
import java.util.List;

public class CustomThreadPoolExecutor {
    private final List<Runnable> tasksQueue;
    private final Thread[] workerThreads;
    private Integer index;

    public CustomThreadPoolExecutor(int poolSize) {
        this.tasksQueue = new ArrayList<>();
        this.workerThreads = new Thread[poolSize];
        this.index = 0;
        startThreads();
    }

    private void startThreads() {
        for (int i = 0; i < workerThreads.length; i++) {
            int finalI = i;
            workerThreads[i] = new Thread(() -> consume(finalI));
            workerThreads[i].start();
        }
    }

    private void consume(int threadId) {
        while (true) {
            Runnable task;
            System.out.printf("Thread: %d is trying to acquire lock\n", Thread.currentThread().getId());
            synchronized (this.tasksQueue) {
                System.out.printf("Thread: %d has acquired lock\n", Thread.currentThread().getId());
                while (index == 0) {
                    System.out.printf("Thread: %d is waiting\n", Thread.currentThread().getId());
                    try {
                        this.tasksQueue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                index -= 1;
                System.out.printf("Thread: %d is consuming task at index: %d\n", Thread.currentThread().getId(), index);
                task = tasksQueue.get(index);
            }
            task.run();
        }
    }

    public void execute(Runnable task) {
        synchronized (tasksQueue) {
            System.out.println("Adding task");
            tasksQueue.add(task);
            index++;
            tasksQueue.notifyAll();
        }
    }
}
