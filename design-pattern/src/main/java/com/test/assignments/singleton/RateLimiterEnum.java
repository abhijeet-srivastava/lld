package com.test.assignments.singleton;

import java.util.concurrent.atomic.AtomicBoolean;

public enum RateLimiterEnum {
    RATE_LIMITER(0, 0);

    private Integer windowSize;
    private Integer threshold;

    private AtomicBoolean isInitialised = new AtomicBoolean(false);
    public void initialize(Integer windowSize, Integer threshold) throws InstantiationException {
        if(isInitialised.get()) {
            throw new InstantiationException("Already  initialised");
        }
        this.windowSize = windowSize;
        this.threshold = threshold;
    }
    public  void showConfig() {
        System.out.printf("Window Size: %d\nThreshold: %d\n", windowSize, threshold);
    }

    private RateLimiterEnum(Integer windowSize, Integer threshold) {
    }

    public boolean isAllowed(Integer timestamp) {
        return true;
    }
}
