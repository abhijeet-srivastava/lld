package com.scheduler.techingschedule;

public class ScheduledClass {
    private final Teacher assignedTo;
    private final TimeSlot slot;

    private final TeachingTask teachingTask;

    public ScheduledClass(Teacher assignedTo, TimeSlot slot, TeachingTask task) {
        this.assignedTo = assignedTo;
        this.slot = slot;
        this.teachingTask = task;
    }

    public Teacher getAssignedTo() {
        return assignedTo;
    }

    public TimeSlot getSlot() {
        return slot;
    }

    public TeachingTask getTeachingTask() {
        return teachingTask;
    }
}
