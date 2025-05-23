package com.scheduler.techingschedule;

import java.util.List;
import java.util.UUID;

public class TeachingTask {

    private final UUID id;
    private final List<String> reuiredSkills;

    private final TimeSlot slot;


    public TeachingTask(List<String> reuiredSkills, TimeSlot slot) {
        this.id = UUID.randomUUID();
        this.reuiredSkills = reuiredSkills;
        this.slot = slot;
    }

    public UUID getId() {
        return id;
    }

    public List<String> getReuiredSkills() {
        return reuiredSkills;
    }

    public TimeSlot getSlot() {
        return slot;
    }
}
