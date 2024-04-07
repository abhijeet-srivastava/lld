package com.test.assignments.singleton;


import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.concurrent.atomic.AtomicBoolean;

public class RateLimiter implements Serializable {

    private static RateLimiter INSTANCE;

    private static Integer windowSize;
    private static Integer requestThreshold;

    private static AtomicBoolean isInitialised = new AtomicBoolean(false);

    public static void initialise(Integer ws, Integer th) throws InstantiationException {
        if(isInitialised.compareAndSet(false, true)) {
            windowSize = ws;
            requestThreshold = th;
        } else {
            throw new InstantiationException("Already Intialised");
        }
    }

    private RateLimiter() {
    }

    public static RateLimiter getInstance() {
        if(!isInitialised.get()) {
            throw new InvalidParameterException("Instance not initialized");
        }
        if(INSTANCE == null) {
            synchronized (RateLimiter.class) {
                if(INSTANCE == null) {
                    INSTANCE = new RateLimiter();
                }
            }
        }
        return INSTANCE;
    }

    public  void showConfig() {
        System.out.printf("Window Size: %d\nThreshold: %d\n", windowSize, requestThreshold);
    }
    /**
     * Protect against Deserialisation
     * @return
     */
    protected Object readResolve() {
        return INSTANCE;
    }

    public boolean isAllowed(Integer timestamp) {
        return true;
    }
}
