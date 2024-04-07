package com.tests.assignments;


public abstract class ScheduleTask {
    protected final ExecutionContext context;

    protected ScheduleTask(ExecutionContext context) {
        this.context = context;
    }

    public void execute() {
        context.execute();
    }

    public abstract boolean isRecurring();

    public abstract ScheduleTask nextScheduledTask();

    public abstract Long nextExecutionTime();
}
