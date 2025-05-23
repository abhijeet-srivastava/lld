package com.scheduler.techingschedule;

import java.time.LocalDateTime;

public class TimeSlot {

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;


    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean overlaps(TimeSlot ts) {
        LocalDateTime maxStart = this.startTime;
        if(maxStart.isBefore(ts.startTime)) {
            maxStart = ts.startTime;
        }
        LocalDateTime minEnd = this.endTime;
        if(minEnd.isAfter(ts.endTime)) {
            minEnd = ts.endTime;
        }
        return maxStart.isBefore(minEnd);
    }
}
