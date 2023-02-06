package com.test.assignments;

import com.test.assignments.models.ScheduledTask;
import com.test.assignments.models.ScheduledTaskType;

import java.util.PriorityQueue;

public class ScheduledExecutorQueue {
    private final PriorityQueue<ScheduledTask> scheduledTasks;
    private final Thread[] workerThreads;

    public ScheduledExecutorQueue(int poolSize) {
        this.scheduledTasks = new PriorityQueue<>();
        this.workerThreads = new Thread[poolSize];
        initializeWorkerThreads();
    }

    private void initializeWorkerThreads() {
        for(int i = 0; i < this.workerThreads.length; i++) {
            this.workerThreads[i] = new Thread(() -> {
                try {
                    polling();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    private void polling() throws InterruptedException {
        while(true) {
            ScheduledTask taskToExecute = null;
            System.out.printf("Thread: %d trying to acquire lock\n", Thread.currentThread().getId());
            synchronized (this.scheduledTasks) {
                System.out.printf("Thread: %d acquired lock\n", Thread.currentThread().getId());
                while (this.scheduledTasks.isEmpty()) {
                    System.out.printf("Thread: %d waiting\n", Thread.currentThread().getId());
                    this.scheduledTasks.wait();
                }
                ScheduledTask task = this.scheduledTasks.peek();
                if(task.getScheduledExecutionTime() < System.currentTimeMillis()) {
                    taskToExecute = this.scheduledTasks.poll();
                }
            }
            if(taskToExecute == null) {
                Thread.sleep(200);
            }
            if(taskToExecute != null && taskToExecute.getScheduledTaskType() == ScheduledTaskType.FIXED_RATE) {
                ScheduledTask scheduledTask = cloneScheduleTask(taskToExecute);
                scheduledTask.setScheduledExecutionTime(System.currentTimeMillis() + taskToExecute.getScheduledPeriodInMillis());
                addScheduleTask(scheduledTask);
            }
            System.out.printf("Thread: %d executing task\n", Thread.currentThread().getId());
            taskToExecute.getTask().run();
            if(taskToExecute != null && taskToExecute.getScheduledTaskType() == ScheduledTaskType.FIXED_DELAY) {
                ScheduledTask scheduledTask = cloneScheduleTask(taskToExecute);
                scheduledTask.setScheduledExecutionTime(System.currentTimeMillis() + taskToExecute.getScheduledPeriodInMillis());
                addScheduleTask(scheduledTask);
            }
        }
    }

    public void addScheduleTask(ScheduledTask scheduledTask) {
        synchronized (this.scheduledTasks) {
            System.out.printf("Adding task to queue\n");
            scheduledTasks.add(scheduledTask);
            scheduledTasks.notifyAll();
        }
    }

    private ScheduledTask cloneScheduleTask(ScheduledTask taskToExecute) {
        ScheduledTask scheduledTask = new ScheduledTask(
                taskToExecute.getTask(),
                taskToExecute.getScheduledExecutionTime(),
                taskToExecute.getScheduledTaskType(),
                taskToExecute.getScheduledPeriodInMillis()
        );
        return scheduledTask;
    }
}
