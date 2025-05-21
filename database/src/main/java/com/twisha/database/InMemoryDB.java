package com.twisha.database;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class InMemoryDB {
    private static volatile InMemoryDB instance;
    private static final ReentrantLock lock = new ReentrantLock();

    private final ConcurrentHashMap<String, Object> store;
    private final LinkedHashMap<String, Boolean> lru;
    private final ConcurrentHashMap<String, Long> ttl;
    private final int maxSize;

    public InMemoryDB(int maxSize) {
        this.maxSize = maxSize;
        this.store = new ConcurrentHashMap<>();
        this.lru = new LinkedHashMap<>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Boolean> eldest) {
                if(this.size() > maxSize) {
                    store.remove(eldest.getKey());
                    ttl.remove(eldest.getKey());
                    return true;
                }
                return false;
            }
        };
        this.ttl = new ConcurrentHashMap<>();
    }

    public static InMemoryDB getInstance(int maxSize) {
        if(instance == null) {
            lock.lock();
            try {
                if(instance == null) {
                    instance = new InMemoryDB(maxSize);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Optional<Object> get(String key) {
        lock.lock();
        try {
            evictExpired();
            if(store.containsKey(key)) {
                lru.get(key);
                return Optional.ofNullable(store.get(key));
            }
            return Optional.empty();
        } finally {
            lock.unlock();
        }
    }

    public boolean set(String key, Object value, Long ttlSeconds) {
        lock.lock();
        try {
            evictExpired();
            store.put(key, value);
            lru.remove(key, true);
            if(ttlSeconds != null) {
                ttl.put(key, System.currentTimeMillis() + ttlSeconds*1000);
            } else {
                ttl.remove(key);
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean delete(String key) {
        lock.lock();
        try {
            evictExpired();
            if(store.containsKey(key)) {
                this.store.remove(key);
                this.lru.remove(key);
                this.ttl.remove(key);
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean exists(String key) {
        lock.lock();
        try {
            evictExpired();
            return this.store.containsKey(key);
        } finally {
            lock.unlock();
        }
    }

    private void evictExpired() {
        long currentTime = System.currentTimeMillis();
        ttl.entrySet().removeIf(entry -> {
            if(entry.getValue() <= currentTime) {
                this.store.remove(entry.getKey());
                lru.remove(entry.getKey());
                return true;
            }
            return false;
        });
    }
}
