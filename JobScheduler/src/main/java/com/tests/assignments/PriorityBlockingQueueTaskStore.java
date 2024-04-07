package com.tests.assignments;

import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueTaskStore implements TaskStore<ScheduleTask> {

    private final PriorityBlockingQueue<ScheduleTask> taskQueue;
    private final Set<ScheduleTask> tasks;

    public PriorityBlockingQueueTaskStore(PriorityBlockingQueue<ScheduleTask> taskQueue, Set<ScheduleTask> tasks) {
        this.taskQueue = taskQueue;
        this.tasks = tasks;
    }

    @Override
    public ScheduleTask peek() {
        return taskQueue.peek();
    }

    @Override
    public ScheduleTask poll() {
        return taskQueue.poll();
    }

    @Override
    public void add(ScheduleTask task) {
        taskQueue.offer(task);
    }

    @Override
    public boolean remove(ScheduleTask task) {
        if (tasks.contains(task)) {
            taskQueue.remove(task);
            tasks.remove(task);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }
}
