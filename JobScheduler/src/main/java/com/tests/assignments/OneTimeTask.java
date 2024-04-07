package com.tests.assignments;

public class OneTimeTask extends ScheduleTask {

    private final long executionTime;

    public OneTimeTask(ExecutionContext context, long executionTime) {
        super(context);
        this.executionTime = executionTime;
    }

    @Override
    public boolean isRecurring() {
        return false;
    }

    @Override
    public ScheduleTask nextScheduledTask() {
        return null;
    }

    @Override
    public Long nextExecutionTime() {
        return executionTime;
    }
}
