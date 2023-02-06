package com.test.assignments.models;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class ScheduledTask implements Comparable<ScheduledTask> {
    @Setter(AccessLevel.NONE)
    private final Runnable task;

    private Long scheduledExecutionTime;

    @Setter(AccessLevel.NONE)
    private final ScheduledTaskType scheduledTaskType;

    @Setter(AccessLevel.NONE)
    private Long scheduledPeriodInMillis;

    public ScheduledTask(Runnable task, Long scheduledExecutionTime, ScheduledTaskType scheduledTaskType) {
        this.task = task;
        this.scheduledExecutionTime = scheduledExecutionTime;
        this.scheduledTaskType = scheduledTaskType;
    }

    public ScheduledTask(Runnable task, Long scheduledExecutionTime, ScheduledTaskType scheduledTaskType, Long scheduledPeriodInMillis) {
        this.task = task;
        this.scheduledExecutionTime = scheduledExecutionTime;
        this.scheduledTaskType = scheduledTaskType;
        this.scheduledPeriodInMillis = scheduledPeriodInMillis;
    }

    @Override
    public int compareTo(ScheduledTask o) {
        return (int)(this.scheduledExecutionTime - o.scheduledExecutionTime);
    }
}
