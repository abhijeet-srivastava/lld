package com.scheduler.techingschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Teacher {

    private final UUID id;
    private final String names;
    private final List<String> skills;

    private final List<ScheduledClass> schedules;

    public Teacher(String names, List<String> skills) {
        this.id = UUID.randomUUID();
        this.names = names;
        this.skills = skills;
        this.schedules = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getNames() {
        return names;
    }

    public List<String> getSkills() {
        return skills;
    }

    public List<ScheduledClass> getSchedules() {
        return schedules;
    }
}
