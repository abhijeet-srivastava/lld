package com.tests.assignments;

public class TaskRunner implements Runnable {
    private final TaskStore<ScheduleTask> taskStore;
    private boolean running;


    public TaskRunner(TaskStore<ScheduleTask> taskStore) {
        this.taskStore = taskStore;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            ScheduleTask task = taskStore.poll();
            if(task == null) {
                break;
            }
            task.execute();
            if(task.isRecurring()) {
                taskStore.add(task.nextScheduledTask());
            }
        }
    }
    public void stop() {
        this.running = false;
    }
}
