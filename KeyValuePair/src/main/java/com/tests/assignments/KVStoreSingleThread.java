package com.tests.assignments;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class KVStoreSingleThread<K, V> implements IKeyValuePairTxn<K,V>, TransactionalKVPair{

    private static final Integer NUM_ID = 100;
    private Set<Integer> activeIds;
    private Set<Integer> inactiveIds;

    private Queue<Integer> availableIds;

    private IsolationLevel isolationLevel;

    private Map<Integer, Map<K,V>> rollBackValues;//Values to be rolled back against txn id

    private Map<K, Set<Integer>> readLocks;//Keys with read locks in each txn

    private Map<K, Integer> writeLock;//Keys in writeLocks with txn

    private Map<K, V> store;

    public enum IsolationLevel {
        READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE
    }

    public KVStoreSingleThread() {
        this(IsolationLevel.READ_COMMITTED);
    }

    public KVStoreSingleThread(IsolationLevel isolationLevel) {
        this.isolationLevel = isolationLevel;
        this.store = new HashMap<>();
        this.activeIds = new HashSet<>();
        this.inactiveIds = new HashSet<>();
        this.availableIds = new ArrayDeque<>(NUM_ID);
        this.rollBackValues = new HashMap<>();
        this.readLocks = new HashMap<>();
        this.writeLock = new HashMap<>();
        for(int id = 1; id <= NUM_ID; id++) {
            this.availableIds.add(id);
        }
        log("KV pair initialised with isolation level " + this.isolationLevel);
    }

    @Override
    public int beginTransaction() {
        int txnId = assignId();
        log("Transaction " + txnId + " started");
        return txnId;
    }
    @Override
    public void commit(int txnId) {
        if(!isActive(txnId)) {
            log("Commit transaction " + txnId + ", already rolled back");
            return;
        }
        log("Commit transaction " + txnId + ", successful");
        cleanUp(txnId);
    }

    @Override
    public void put(int txnId, K key, V value) {
        if(!isActive(txnId)) {
            return;
        }
        if(hasConflictWrite(txnId, key)) {
            log("Transaction " + txnId + " get key " + key + " failed, rolled back");
            rollBack(txnId);
            return;
        }
        this.rollBackValues.computeIfAbsent(txnId, t -> new HashMap<>());
        // only add the (key, original value) if this is first update
        // because all the later update will all rollback to this original value
        if(!rollBackValues.get(txnId).containsKey(key)) {
            rollBackValues.get(txnId).put(key, store.get(key));
        }
        writeLock.put(key, txnId);
        store.put(key, value);
        log("Transaction " + txnId + " put (" + key + ", " + value + ") successful");
    }

    @Override
    public V get(int txnId, K key) {
        if(!isActive(txnId)) {
            return null;
        }
        // If level is IsolationLevel.READ_UNCOMMITTED, we don't need to obtain read lock
        if(this.isolationLevel == IsolationLevel.READ_UNCOMMITTED) {
            log("Transaction " + txnId + " get key " + key + " successful, returns " + store.get(key));
            return this.store.get(key);
        }
        if(hasConflictRead(txnId, key)) {
            log("Transaction " + txnId + " get key " + key + " failed, rolled back");
            rollBack(txnId);
            return null;
        }
        if(this.store.containsKey(key)) {
            if(isolationLevel == IsolationLevel.REPEATABLE_READ || isolationLevel == IsolationLevel.SERIALIZABLE) {
                this.readLocks.computeIfAbsent(key, k -> new HashSet<>()).add(txnId);
            }
            /*
             * If level is IsolationLevel.READ_COMMITTED, we don't need to obtain any lock. Remember
             * when isolation level is read committed, a read statement only holds a read lock during
             * the single statement, it doesn't hold the read lock for the entire transaction.
             * In the case of single thread, we don't need to do anything here.
             */
        } else if(isolationLevel == IsolationLevel.SERIALIZABLE) {
            //If Isolation level is serializable
            this.readLocks.computeIfAbsent(key, k -> new HashSet<>()).add(txnId);
        }
        log("Transaction " + txnId + " get key " + key + " successful, returns " + store.get(key));
        return store.get(key);
    }

    public void printStore() {
        System.out.println("Store:");
        for(Map.Entry<K, V> entry: this.store.entrySet()) {
            System.out.printf("%s <==> %s\n", entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void rollBack(int txnId) {
        // roll back the key-value pairs that's changed
        if(rollBackValues.containsKey(txnId)) {
            Map<K, V> originalValues = this.rollBackValues.get(txnId);
            for(Map.Entry<K, V> entry: originalValues.entrySet()) {
                if(entry.getValue() == null) {
                    this.store.remove(entry.getKey());
                } else {
                    this.store.put(entry.getKey(), entry.getValue());
                }
            }
        }
        cleanUp(txnId);
    }

    private void releaseLocks(int txnId) {
        for(K key: this.readLocks.keySet()) {
            if(this.readLocks.get(key).contains(txnId)) {
                this.readLocks.get(key).remove(txnId);
            }
        }
        this.readLocks.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        this.writeLock.entrySet().removeIf(entry -> entry.getValue() == txnId);
    }

    private boolean hasConflictRead(int txnId, K key) {
        boolean result = true;
        if(this.isolationLevel == IsolationLevel.READ_UNCOMMITTED) {
            return false;
        }
        // if writeLock on this key exists and the holder is not the current transaction, then there is a conflict
        if(this.writeLock.containsKey(key) && this.writeLock.get(key) != txnId) {
            return true;
        }
        return false;
    }

    private boolean hasConflictWrite(int transId, K key) {
        boolean result = true;
        /*
         * We say there is no conflict when
         * 1. There is no read lock on this key or the only read lock on this key is held by this transaction AND
         * 2. There is no write lock on this key or the only write lock on this key is held by this transaction
         */
        if ((!readLocks.containsKey(key)
                || (readLocks.get(key).size() == 1 && readLocks.get(key).contains(transId)))
                && (!writeLock.containsKey(key) || (writeLock.get(key) == transId))) {
            result = false;
        }

        return result;
    }

    private int assignId() {
        if(this.availableIds.isEmpty()) {
            throw new RuntimeException("Run out of txn ids");
        }
        int txnId = this.availableIds.poll();
        this.activeIds.add(txnId);
        return txnId;
    }

    private void cleanUp(int txnId) {
        releaseLocks(txnId);
        this.rollBackValues.remove(txnId);
        this.activeIds.remove(txnId);
        this.inactiveIds.add(txnId);
    }

    private boolean isActive(int txnId) {
        if(this.inactiveIds.contains(txnId)) {
            return false;
        } else if(!this.activeIds.contains(txnId)) {
            throw new RuntimeException("Transaction Id not valid");
        }
        return true;
    }

    private void log(String message) {
        System.out.printf("%s - %s%c", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), message, '\n');
    }
}
