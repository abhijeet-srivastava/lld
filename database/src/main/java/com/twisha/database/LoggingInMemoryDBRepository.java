package com.twisha.database;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingInMemoryDBRepository implements InMemoryDBRepository {

    private final InMemoryDB delegate;

    private final Logger logger;

    public LoggingInMemoryDBRepository(InMemoryDB delegate) {
        this.delegate = delegate;
        this.logger = Logger.getLogger("InMemoryLogger");
    }


    @Override
    public boolean set(String key, Object value, Long ttlSeconds) {
        logger.log(Level.INFO, "Setting Key:{}, Value: {}, TTL: {}");
        return this.delegate.set(key, value, ttlSeconds);
    }

    @Override
    public Optional<Object> get(String key) {
        logger.log(Level.INFO, "Getting value");
        return this.delegate.get(key);
    }

    @Override
    public boolean delete(String key) {
        logger.log(Level.INFO, " Deleting key");
        return this.delegate.delete(key);
    }

    @Override
    public boolean exists(String key) {
        logger.log(Level.INFO, "Check for existence");
        return this.delegate.exists(key);
    }
}
