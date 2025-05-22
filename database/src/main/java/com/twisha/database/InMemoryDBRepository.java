package com.twisha.database;

import java.util.Optional;

public interface InMemoryDBRepository {
    boolean set(String key, Object value, Long ttlSeconds);
    Optional<Object> get(String key);
    boolean delete(String key);
    boolean exists(String key);
}
