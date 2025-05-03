package com.tests.assignments.tst;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class KVStoreSingleThread<K, V> {
    private static final int NUM_ID = 1000;
    private HashSet<Integer> activeIds; // store active transaction ids
    private HashSet<Integer> inactiveIds; // store transaction ids that are committed or rolled back
    private Queue<Integer> availableIds; // transaction id pool
    private HashMap<K, V> store; // stores key-value pairs
    private IsolationLevel level;
    private HashMap<Integer, HashMap<K, V>> rollbackValues; // stores the original value for all key-value pairs a transaction has modified
    private HashMap<K, HashSet<Integer>> readLock; // stores the read locks, the key is the Key, and values are transaction ids in a set, because we can have multiple read keys on a key-value pair
    private HashMap<K, Integer> writeLock;  // stores the write locks, the key is the Key, and value is the transaction id that currently holds the lock

    public static enum IsolationLevel {
        READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE
    }

    public KVStoreSingleThread() {
        this(IsolationLevel.READ_COMMITTED);
    }

    public KVStoreSingleThread(IsolationLevel level) {
        store = new HashMap<>();
        activeIds = new HashSet<>();
        inactiveIds = new HashSet<>();
        availableIds = new LinkedList<>();
        rollbackValues = new HashMap<>();
        readLock = new HashMap<>();
        writeLock = new HashMap<>();
        this.level = level;

        for(int i = 1; i <= NUM_ID; i++){
            availableIds.offer(i);
        }
        log("KV store initialized: Isolation level " + level);
    }

    public int beginTransaction() {
        int id = assignId();

        log("Transaction " + id + " started.");
        return id;
    }

    public void commit(int transId) {
        if (!isActive(transId)) {
            log("Commit transaction " + transId + ", already rolled back");
            return;
        }

        log("Commit transaction " + transId + ", successful");
        cleanUp(transId);
    }

    public void put(int transId, K key, V value) {
        if (!isActive(transId)) return;

        if (hasConflictWrite(transId, key)) {
            log("Transaction " + transId + " put (" + key + ", " + value + ") failed, rolled back.");
            rollback(transId);
            return;
        }

        if (!rollbackValues.containsKey(transId)) {
            rollbackValues.put(transId, new HashMap<>());
        }
        // only add the (key, original value) if this is first update
        // because all the later update will all rollback to this original value
        if (!rollbackValues.get(transId).containsKey(key)) {
            rollbackValues.get(transId).put(key, store.get(key));
        }
        writeLock.put(key, transId);
        store.put(key, value);
        log("Transaction " + transId + " put (" + key + ", " + value + ") successful");
    }

    public V get(int transId, K key) {
        if (!isActive(transId)) return null;

        // If level is IsolationLevel.READ_UNCOMMITTED, we don't need to obtain read lock
        if (level == IsolationLevel.READ_UNCOMMITTED) {
            log("Transaction " + transId + " get key " + key + " successful, returns " + store.get(key));
            return store.get(key);
        }

        if (hasConflictRead(transId, key)) {
            log("Transaction " + transId + " get key " + key + " failed, rolled back");
            rollback(transId);
            return null;
        }

        if (store.containsKey(key)) {
            if (level == IsolationLevel.REPEATABLE_READ || level == IsolationLevel.SERIALIZABLE) {
                readLock.computeIfAbsent(key, k-> new HashSet<>()).add(transId);
            }
            /*
             * If level is IsolationLevel.READ_COMMITTED, we don't need to obtain any lock. Remember
             * when isolation level is read committed, a read statement only holds a read lock during
             * the single statement, it doesn't hold the read lock for the entire transaction.
             * In the case of single thread, we don't need to do anything here.
             */

        } else if (level == IsolationLevel.SERIALIZABLE) {
            readLock.computeIfAbsent(key, k-> new HashSet<>()).add(transId);
        }
        log("Transaction " + transId + " get key " + key + " successful, returns " + store.get(key));
        return store.get(key);
    }

    public void printStore() {
        System.out.println("Store:");
        for (K k : store.keySet()) {
            System.out.println("(" + k + ", " + store.get(k) + ")");
        }
    }

    private void rollback(int transId) {
        // roll back the key-value pairs that's changed
        if (rollbackValues.containsKey(transId)) {
            HashMap<K, V> originalValues = rollbackValues.get(transId);
            for(K key: originalValues.keySet()) {
                if (originalValues.get(key) == null) {
                    store.remove(key);
                } else {
                    store.put(key, originalValues.get(key));
                }
            }
        }
        cleanUp(transId);
    }

    private void releaseLocks(int transId) {
        // release read lock
        for(K key: readLock.keySet()) {
            if (readLock.get(key).contains(transId)) {
                readLock.get(key).remove(transId);
            }
        }

        readLock.entrySet().removeIf(entry -> (entry.getValue().isEmpty()));

        // release write lock
        writeLock.entrySet().removeIf(entry -> (transId == entry.getValue()));
    }

    private boolean hasConflictRead(int transId, K key) {
        boolean result = true;
        switch(level) {
            case READ_UNCOMMITTED:
                result = false;
                break;
            case READ_COMMITTED:
            case REPEATABLE_READ:
            case SERIALIZABLE:
                // if writeLock on this key exists and the holder is not the current transaction, then there is a conflict
                result = writeLock.get(key) != null && writeLock.get(key) != transId;
                break;
        }
        return result;
    }

    private boolean hasConflictWrite(int transId, K key) {
        boolean result = true;

        /*
         * We say there is no conflict when
         * 1. There is no read lock on this key or the only read lock on this key is held by this transaction AND
         * 2. There is no write lock on this key or the only write lock on this key is held by this transaction
         */
        if ((!readLock.containsKey(key) || (readLock.get(key).size() == 1 && readLock.get(key).contains(transId)))
                && (!writeLock.containsKey(key) || (writeLock.get(key) == transId))) {
            result = false;
        }

        return result;
    }

    private int assignId() {
        if (availableIds.isEmpty()) {
            throw new RuntimeException("Run out of transaction Ids");
        }
        int newId = availableIds.poll();
        activeIds.add(newId);
        return newId;
    }

    private void cleanUp(int transId) {
        releaseLocks(transId);
        rollbackValues.remove(transId);
        activeIds.remove(transId);
        inactiveIds.add(transId);
    }

    private boolean isActive(int transId) {
        // If inactiveIds contain the transId, it means
        // it has been committed or rolled back before
        if (inactiveIds.contains(transId)) {
            return false;
        }

        if (!activeIds.contains(transId)) {
            throw new RuntimeException("Transaction Id not valid");
        }

        return true;
    }

    private void log(String msg) {
        System.out.println(msg);
    }
}
