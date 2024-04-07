package com.tests.assignments;

public interface TaskStore<T extends ScheduleTask> {
    T peek();
    T poll();
    void add(T task);

    boolean remove(T task);

    boolean isEmpty();
}
