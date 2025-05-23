package com.scheduler.techingschedule.strategy;

import com.scheduler.techingschedule.ScheduledClass;
import com.scheduler.techingschedule.Teacher;
import com.scheduler.techingschedule.TeachingTask;

import java.util.List;

public class FirstAvailableAssigningStrategy implements AssigningStrategy {
    @Override
    public ScheduledClass assignTeacher(List<Teacher> teachers, TeachingTask task) {
        for(Teacher teacher: teachers) {
            boolean canBeAssigned = true;
            for(ScheduledClass sc: teacher.getSchedules()) {
                if(task.getSlot().overlaps(sc.getSlot())) {
                    canBeAssigned = false;
                    break;
                }
            }
            if(canBeAssigned) {
                return new ScheduledClass(teacher, task.getSlot(), task);
            }
        }
        return null;
    }
}
