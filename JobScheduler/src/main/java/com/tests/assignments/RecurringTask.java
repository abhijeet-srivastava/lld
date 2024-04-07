package com.tests.assignments;

public class RecurringTask extends ScheduleTask {

    private final long executionTime;
    private final long interval;

    public RecurringTask(ExecutionContext context, long executionTime, long interval) {
        super(context);
        this.executionTime = executionTime;
        this.interval = interval;
    }

    @Override
    public boolean isRecurring() {
        return true;
    }

    @Override
    public ScheduleTask nextScheduledTask() {
        return new RecurringTask(context, executionTime+interval, interval);
    }

    @Override
    public Long nextExecutionTime() {
        return executionTime;
    }
}
