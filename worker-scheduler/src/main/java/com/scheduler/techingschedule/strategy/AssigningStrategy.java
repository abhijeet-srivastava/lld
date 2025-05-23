package com.scheduler.techingschedule.strategy;

import com.scheduler.techingschedule.ScheduledClass;
import com.scheduler.techingschedule.Teacher;
import com.scheduler.techingschedule.TeachingTask;

import java.util.List;

public interface AssigningStrategy {

    ScheduledClass assignTeacher(List<Teacher> teachers, TeachingTask task);
}
