package com.twisha.database;

import java.util.Optional;

public class InMemoryDBRepositoryImpl implements InMemoryDBRepository {

    private final InMemoryDB db;

    public InMemoryDBRepositoryImpl() {
        this.db = InMemoryDB.getInstance(1000);
    }

    @Override
    public boolean set(String key, Object value, Long ttlSeconds) {
        return false;
    }

    @Override
    public Optional<Object> get(String key) {
        return Optional.empty();
    }

    @Override
    public boolean delete(String key) {
        return false;
    }

    @Override
    public boolean exists(String key) {
        return false;
    }
}
