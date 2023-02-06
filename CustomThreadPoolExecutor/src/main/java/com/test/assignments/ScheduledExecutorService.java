package com.test.assignments;

import com.test.assignments.models.ScheduledTask;
import com.test.assignments.models.ScheduledTaskType;

import java.util.concurrent.TimeUnit;

public class ScheduledExecutorService implements IScheduledExecutorService{
    private final ScheduledExecutorQueue scheduledExecutorQueue;

    public ScheduledExecutorService(int poolSize) {
        this.scheduledExecutorQueue = new ScheduledExecutorQueue(poolSize);
    }


    @Override
    public void schedule(Runnable command, long delay, TimeUnit unit) {
        Long currentTimeMillis = System.currentTimeMillis();
        Long delayInMillis = unit.toMillis(delay);
        scheduledExecutorQueue.addScheduleTask(new ScheduledTask(command, currentTimeMillis + delayInMillis, ScheduledTaskType.ONE_SHOT));
    }

    @Override
    public void scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        Long currentTimeMillis = System.currentTimeMillis();
        Long delayInMillis = unit.toMillis(initialDelay);
        scheduledExecutorQueue.addScheduleTask(new ScheduledTask(command, currentTimeMillis + delayInMillis, ScheduledTaskType.FIXED_RATE, unit.toMillis(period)));
    }

    @Override
    public void scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        Long currentTimeMillis = System.currentTimeMillis();
        Long delayInMillis = unit.toMillis(initialDelay);
        scheduledExecutorQueue.addScheduleTask(new ScheduledTask(command, currentTimeMillis + delayInMillis, ScheduledTaskType.FIXED_RATE, unit.toMillis(delay)));
    }
}
