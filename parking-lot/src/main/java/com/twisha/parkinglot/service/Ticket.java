package com.twisha.parkinglot.service;

import java.time.Duration;
import java.time.LocalDateTime;

public class Ticket {

    private LocalDateTime entryTs;
    private LocalDateTime exitTs;

    private Long duration;
    public Ticket(LocalDateTime entryTs, LocalDateTime exitTs) {
        this.entryTs = entryTs;
        this.exitTs = exitTs;
        Duration duration = Duration.between(entryTs, exitTs);
        this.duration = duration.toMinutes();
    }

    public Long getDuration() {
        return duration;
    }
}
