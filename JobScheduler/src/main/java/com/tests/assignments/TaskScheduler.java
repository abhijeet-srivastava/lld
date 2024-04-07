package com.tests.assignments;

import java.util.ArrayList;
import java.util.List;

public class TaskScheduler {
    private final List<Runnable> threads;

    private final TaskStore<ScheduleTask> taskStore;

    public TaskScheduler(ExecutorConfig executorConfig, TaskStore<ScheduleTask> taskStore) {
        this.taskStore = taskStore;
        this.threads = new ArrayList<>();
        for(int i = 0; i < executorConfig.getNumThreads(); i++) {
            Thread thread = new Thread(new TaskRunner(taskStore));
            thread.start();
            threads.add(thread);
        }
    }
    public void stop() {
        threads.forEach(t -> ((TaskRunner) t).stop());
    }
}
